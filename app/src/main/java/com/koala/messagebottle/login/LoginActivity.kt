package com.koala.messagebottle.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.SignInButton
import com.koala.messagebottle.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    companion object {
        private const val REQ_GOOGLE_ONE_TAP = 2
    }

    private lateinit var container: View
    private lateinit var containerLoggedIn: View
    private lateinit var containerLoggedOut: View
    private lateinit var btnSignInGoogle: SignInButton
    private lateinit var btnSignInAnonymous: Button
    private lateinit var btnSignOut: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setContentView(
            ComposeView(this).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    LoginViewScreen()
                }
            }
        )

//        return ComposeView(requireContext()).apply {
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//            setContent {
//                GetMessageView()
//            }
//        }

        // configure views
//        container = findViewById(R.id.container)
//        containerLoggedIn = findViewById(R.id.containerLoggedIn)
//        containerLoggedOut = findViewById(R.id.containerLoggedOut)
//        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
//        btnSignInAnonymous = findViewById(R.id.btnSignInAnonymous)
//        btnSignOut = findViewById(R.id.btnSignOut)
//        progressBar = findViewById(R.id.progressBar)
//
//        // configure click handlers
//        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD)
//        btnSignInGoogle.setOnClickListener { initiateLoginWithGoogle() }
//        btnSignInAnonymous.setOnClickListener { viewModel.initiateAnonymousLogin() }
//        btnSignOut.setOnClickListener { viewModel.initiateSignOut() }


        // configure google
        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.firebase_webclient_id)) // Your server's client ID, not your Android client ID.
                    .setFilterByAuthorizedAccounts(false) // Only show accounts previously used to sign in.
                    .build()
            )
            .setAutoSelectEnabled(true) // Automatically sign in when exactly one credential is retrieved.
            .build()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_GOOGLE_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            Timber.d("Token received from successful Google SignIn.")
                            viewModel.initiateLoginWithGoogle(idToken)
                        }

                        else -> {
                            // Shouldn't happen.
                            Timber.e("No ID token!")
                        }
                    }
                } catch (e: RuntimeException) {
                    Timber.e("There was a problem initiating Google One Tap Sign in!")
                }
            }
        }
    }

    private fun initiateLoginWithGoogle() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, REQ_GOOGLE_ONE_TAP,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Timber.e("Couldn't start One Tap UI: ${e.localizedMessage}", e)
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Timber.w(e.localizedMessage)
            }

    }

    // All imperative logic replaced by composable view
//    private fun displayLoginSuccessful() {
//        val welcomeMessage = getString(R.string.sign_in_success, "Dear Tester")
//        Snackbar.make(container, welcomeMessage, Snackbar.LENGTH_SHORT).show()
//    }
//
//    private fun displayLoginFailed() = Snackbar
//        .make(container, R.string.sign_in_failed, Snackbar.LENGTH_SHORT)
//        .show()
//
//    private fun showSignedInContainer() {
//        containerLoggedIn.visibility = View.VISIBLE
//        containerLoggedOut.visibility = View.GONE
//    }
//
//    private fun showSignedOutContainer() {
//        containerLoggedIn.visibility = View.GONE
//        containerLoggedOut.visibility = View.VISIBLE
//    }
//
//    private fun showProgressBar() {
//        progressBar.visibility = View.VISIBLE
//    }
//
//    private fun hideProgressBar() {
//        progressBar.visibility = View.INVISIBLE
//    }
}