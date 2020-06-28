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
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.R
import com.koala.messagebottle.login.data.GetCreateUserDataModel
import com.koala.messagebottle.login.data.UserService
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val TAG = "LoginActivity"
private const val REQUEST_CODE_GOOGLE = 9001

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var userService: UserService

    private lateinit var btnSignInGoogle: SignInButton
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar = findViewById(R.id.progressBar)
        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)

        configureUserService()

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

        firebaseAuth = Firebase.auth
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        btnSignInGoogle = findViewById(R.id.btnSignInGoogle)
        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD)
        btnSignInGoogle.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE)
        }
    }

    // TODO: clean up
    // this shouldn't be happening here
    // let's move toward dagger, etc
    private fun configureUserService() {
        val retrofit = Retrofit.Builder()
            .baseUrl(UserService.API_URL)
            .client(getHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        userService = retrofit.create(UserService::class.java)
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
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "firebaseAuthWithGoogle:success")
                    val user: FirebaseUser = firebaseAuth.currentUser!!

                    Log.w(TAG, "Getting FirebaseUser's IdToken")
                    val taskGetIdToken: Task<GetTokenResult> = user.getIdToken(false)
                    taskGetIdToken.addOnSuccessListener { result: GetTokenResult ->

                        // TODO: call backend for getCreation of User
                        // get token to provide to our backend for verification
                        Log.w(TAG, "Received token result...")
                        Log.w(TAG, "Token - [${result.token}]")
                        Log.w(TAG, "Signin Provider - [${result.signInProvider}]")
                        result.claims.forEach {
                            Log.w(TAG, "[${it.key}] - [${it.value}]")
                        }

                        makeRequestToMybackend(result.token!!)

                        displayGoogleSignSuccess(user)
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                    taskGetIdToken.addOnFailureListener {
                        Log.w(TAG, "taskGetIdToken:failure", it)
                        displayGoogleSignInFailed()
                    }


                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    displayGoogleSignInFailed()
                }

                hideProgressBar()
            }
    }

    private fun makeRequestToMybackend(token: String) {
        val getCreateUserDataModel = GetCreateUserDataModel(token)

        userService.getCreateUser(getCreateUserDataModel)
    }

    private fun getHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor { chain ->
            val requestWithUserAgent = chain.request().newBuilder()
                .header("User-Agent", "My custom user agent")
                .build()
            chain.proceed(requestWithUserAgent)
        }
        return okHttpBuilder.build()
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