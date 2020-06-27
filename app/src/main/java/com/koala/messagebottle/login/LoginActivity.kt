package com.koala.messagebottle.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.R
import kotlinx.android.synthetic.main.activity_login.*

private const val TAG = "LoginActivity"
private const val REQUEST_CODE_GOOGLE = 9001

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var btnSignInGoogle: SignInButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progressBar)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)

        configureGoogleSignInButton()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_CODE_GOOGLE) {
            handleGoogleSigninResult(data!!)
        } else {
            Log.w(TAG, "unsupported request code: [$requestCode]")
        }
    }

    private fun configureGoogleSignInButton() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        auth = Firebase.auth
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD)
        btnSignInGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE)
        }
    }

    /**
     * After a user successfully signs in to google, get an ID token from the GoogleSignInAccount object,
     * exchange it for a Firebase credential, and authenticate with Firebase using the Firebase credential:
     */
    private fun handleGoogleSigninResult(intent: Intent) = try {
        val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
        val account = task.getResult(ApiException::class.java)!!
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)

        val idToken: String = account.idToken!!
        firebaseAuthWithGoogle(idToken)
    } catch (e: ApiException) {
        Log.e(TAG, "Google sign in failed", e)
        displayGoogleSignInFailed()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        showProgressBar()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "firebaseAuthWithGoogle:success")
                    val user: FirebaseUser = auth.currentUser!!
                    displayGoogleSignSuccess(user)

                    setResult(Activity.RESULT_OK)
                    finish()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    displayGoogleSignInFailed()
                }

                hideProgressBar()
            }
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