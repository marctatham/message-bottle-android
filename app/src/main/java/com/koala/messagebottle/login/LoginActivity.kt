package com.koala.messagebottle.login

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.SignInButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseUser
import com.koala.messagebottle.BaseApplication
import com.koala.messagebottle.R
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var btnSignInGoogle: SignInButton
    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Creates an instance of Login component by grabbing the factory from the app graph
        // and injects this activity to that Component
        //(application as BaseApplication).appComponent.loginComponent().create().inject(this)
        val application = (application as BaseApplication)
        val loginComponent = application.appComponent.loginComponent().create(this)
        loginComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
        progressBar = findViewById(R.id.progressBar)

        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD)
        btnSignInGoogle.setOnClickListener {
            viewModel.initiateLoginWithGoogle()
        }

        // TODO: observe the livedata exposed by the viewmodel
    }

    private fun displayGoogleSignInFailed() {
        txtSignInFailed.visibility = View.VISIBLE
        Snackbar.make(container, R.string.sign_in_failed, Snackbar.LENGTH_SHORT).show()
    }

    private fun displayGoogleSignSuccess(user: FirebaseUser) {
        val displayName = user.displayName
        val welcomeMessage = getString(R.string.sign_in_success, displayName)

        val snackbar = Snackbar.make(container, welcomeMessage, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}