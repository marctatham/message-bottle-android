package com.koala.messagebottle.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.gms.common.SignInButton
import com.google.android.material.snackbar.Snackbar
import com.koala.messagebottle.BaseApplication
import com.koala.messagebottle.R
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var containerLoggedIn: View
    private lateinit var containerLoggedOut: View
    private lateinit var btnSignInGoogle: SignInButton
    private lateinit var btnSignOut: Button
    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        val application = (application as BaseApplication)
        val loginComponent = application.appComponent.loginComponent().create(this)
        loginComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        containerLoggedIn = findViewById(R.id.containerLoggedIn)
        containerLoggedOut = findViewById(R.id.containerLoggedOut)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
        btnSignOut = findViewById(R.id.btnSignOut)
        progressBar = findViewById(R.id.progressBar)

        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD)
        btnSignInGoogle.setOnClickListener {
            viewModel.initiateLoginWithGoogle()
        }

        btnSignOut.setOnClickListener {
            viewModel.initiateSignOut()
        }

        viewModel.state.observe(this) { state: State ->
            when (state) {

                State.Anonymous -> {
                    showSignedOutContainer()
                    hideProgressBar()
                }

                State.Loading -> showProgressBar()

                State.LoggedInUser -> {
                    showSignedInContainer()
                    hideProgressBar()
                    displayLoginSuccessful()
                }

                State.Failure -> {
                    displayLoginFailed()
                    hideProgressBar()
                }
            }
        }
    }

    private fun displayLoginSuccessful() {
        val welcomeMessage = getString(R.string.sign_in_success, "Dear Tester")
        Snackbar.make(container, welcomeMessage, Snackbar.LENGTH_LONG).show()
    }

    private fun displayLoginFailed() = Snackbar
        .make(container, R.string.sign_in_failed, Snackbar.LENGTH_LONG)
        .show()

    private fun showSignedInContainer() {
        containerLoggedIn.visibility = View.VISIBLE
        containerLoggedOut.visibility = View.GONE
    }

    private fun showSignedOutContainer() {
        containerLoggedIn.visibility = View.GONE
        containerLoggedOut.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}