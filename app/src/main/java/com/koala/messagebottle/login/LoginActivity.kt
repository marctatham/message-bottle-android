//package com.koala.messagebottle.login
//
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.ProgressBar
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.common.SignInButton
//import com.google.android.material.snackbar.Snackbar
//import com.koala.messagebottle.R
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class LoginActivity : ComponentActivity() {
//
//    private lateinit var container: View
//    private lateinit var containerLoggedIn: View
//    private lateinit var containerLoggedOut: View
//    private lateinit var btnSignInGoogle: SignInButton
//    private lateinit var btnSignInAnonymous: Button
//    private lateinit var btnSignOut: Button
//    private lateinit var progressBar: ProgressBar
//
//    private val viewModel: LoginViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        container = findViewById(R.id.container)
//        containerLoggedIn = findViewById(R.id.containerLoggedIn)
//        containerLoggedOut = findViewById(R.id.containerLoggedOut)
//        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
//        btnSignInAnonymous = findViewById(R.id.btnSignInAnonymous)
//        btnSignOut = findViewById(R.id.btnSignOut)
//        progressBar = findViewById(R.id.progressBar)
//
//        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD)
//        btnSignInGoogle.setOnClickListener {
//            viewModel.initiateLoginWithGoogle()
//        }
//
//        btnSignInAnonymous.setOnClickListener {
//            viewModel.initiateAnonymousLogin()
//        }
//
//        btnSignOut.setOnClickListener {
//            viewModel.initiateSignOut()
//        }
//
//        viewModel.state.observe(this) { state: State ->
//            when (state) {
//
//                State.Anonymous -> {
//                    showSignedOutContainer()
//                    hideProgressBar()
//                }
//
//                State.Loading -> showProgressBar()
//
//                State.LoggedInUser -> {
//                    showSignedInContainer()
//                    hideProgressBar()
//                    displayLoginSuccessful()
//                }
//
//                State.Failure -> {
//                    displayLoginFailed()
//                    hideProgressBar()
//                }
//            }
//        }
//    }
//
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
//}