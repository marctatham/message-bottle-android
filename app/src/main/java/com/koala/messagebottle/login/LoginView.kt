package com.koala.messagebottle.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.koala.messagebottle.R
import com.koala.messagebottle.common.ui.fakeClickHandler

@Composable
fun LoginViewScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state: State by viewModel.state.collectAsStateWithLifecycle()
    val isLoading = state is State.Loading


    val clickGoogle = { Toast.makeText(context, "SignInGoogle", Toast.LENGTH_SHORT).show() }
    val clickAnon = { Toast.makeText(context, "SignIn ANON", Toast.LENGTH_SHORT).show() }
    val clickLogout = { Toast.makeText(context, "sign OUT", Toast.LENGTH_SHORT).show() }

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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onLogoutHandler,
            modifier = Modifier.padding(16.dp),
        ) { Text(text = context.getString(R.string.sign_out)) }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Preview
@Composable
fun UnauthenticatedView(
    isLoading: Boolean = false,
    onGoogleLoginHandler: () -> Unit = fakeClickHandler,
    onAnonLoginHandler: () -> Unit = fakeClickHandler,
) {
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = onGoogleLoginHandler,
                    modifier = Modifier.padding(16.dp),
                ) { Text(text = context.getString(R.string.btnSignIn)) }

                Button(
                    onClick = onAnonLoginHandler,
                    modifier = Modifier.padding(16.dp),
                ) { Text(text = context.getString(R.string.btnSignInAnonymously)) }
            }

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    strokeWidth = 4.dp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
