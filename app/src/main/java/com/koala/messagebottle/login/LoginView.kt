package com.koala.messagebottle.login

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult.Companion.EXTRA_SEND_INTENT_EXCEPTION
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.koala.messagebottle.R
import com.koala.messagebottle.common.ui.fakeClickHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

@Composable
fun LoginViewScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    // configure & remember launcher that facilitates the Google One Tap Sign-in UI
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != Activity.RESULT_OK) {
            // The user cancelled the login, was it due to an Exception?
            if (result.data?.action == ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST) {
                val exception = result.data?.getSerializableExtra(EXTRA_SEND_INTENT_EXCEPTION)
                Timber.e("Couldn't sign in using Google One Tap: $exception")
                Toast.makeText(
                    context,
                    "There was a problem signing in with Google",
                    Toast.LENGTH_SHORT
                ).show()
            }

            return@rememberLauncherForActivityResult
        }
        val oneTapClient = Identity.getSignInClient(context)
        val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
        val idToken = credential.googleIdToken
        if (idToken != null) {
            // Got an ID token from Google. Use it to authenticate with your backend.
            Timber.i("Sign in with Google successful, attempting authenticate to backend with idToken")
            viewModel.initiateLoginWithGoogle(idToken)
        } else {
            Timber.e("Null idToken from google, signin to google failed")
        }
    }

    // Create a scope that is automatically cancelled  if the
    // user closes the app while async work is still happening
    val scope = rememberCoroutineScope()

    // configure click handlers
    val clickLogout = { viewModel.initiateSignOut() }
    val clickAnon = { viewModel.initiateAnonymousLogin() }
    val clickGoogle: () -> Unit = {
        scope.launch {
            signIn(
                context = context,
                launcher = launcher
            )
        }
    }

    val state: State by viewModel.state.collectAsStateWithLifecycle()
    val isLoading = state is State.Loading
    when (state) {
        State.Anonymous,
        State.Failure,
        State.Loading -> UnauthenticatedView(
            isLoading = isLoading,
            onGoogleLoginHandler = clickGoogle,
            onAnonLoginHandler = clickAnon,
        )

        State.LoggedInUser -> AuthenticatedView(
            onLogoutHandler = clickLogout,
        )
    }
}

@Preview
@Composable
fun AuthenticatedView(
    onLogoutHandler: () -> Unit = fakeClickHandler,
) {
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        Button(
            onClick = onLogoutHandler,
            modifier = Modifier.align(Alignment.Center),
        ) { Text(text = context.getString(R.string.sign_out)) }
    }
}


@Preview
@Composable
fun UnauthenticatedView(
    isLoading: Boolean = true,
    onGoogleLoginHandler: () -> Unit = fakeClickHandler,
    onAnonLoginHandler: () -> Unit = fakeClickHandler,
) {
    val context = LocalContext.current
    Box(Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Button(
                onClick = onGoogleLoginHandler,
                modifier = Modifier.padding(8.dp),
            ) { Text(text = context.getString(R.string.btnSignIn)) }

            Button(
                onClick = onAnonLoginHandler,
                modifier = Modifier.padding(8.dp),
            ) { Text(text = context.getString(R.string.btnSignInAnonymously)) }
        }

        if (isLoading) {
            CircularProgressIndicator(
                strokeWidth = 4.dp,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
            )
        }
    }
}

suspend fun signIn(context: Context, launcher: ActivityResultLauncher<IntentSenderRequest>) {
    val oneTapClient = Identity.getSignInClient(context)
    val signInRequest = BeginSignInRequest.builder()
        .setPasswordRequestOptions(
            BeginSignInRequest.PasswordRequestOptions.builder()
                .setSupported(true)
                .build()
        )
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(context.getString(R.string.firebase_webclient_id)) // Your server's client ID, not your Android client ID.
                .setFilterByAuthorizedAccounts(false) // Only show accounts previously used to sign in.
                .build()
        )
        .setAutoSelectEnabled(true) // Automatically sign in when exactly one credential is retrieved.
        .build()

    try {
        // Use await() from https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-play-services
        // Instead of listeners that aren't cleaned up automatically
        val result = oneTapClient.beginSignIn(signInRequest).await()

        // Now construct the IntentSenderRequest the launcher requires
        val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
        launcher.launch(intentSenderRequest)
    } catch (e: Exception) {
        // No saved credentials found. Launch the One Tap sign-up flow, or
        // do nothing which results in no state changes & therefore continues
        // presenting the UnauthenticatedView.
        Timber.e("there was a problem Signing in with Google One Tap", e)
    }
}

