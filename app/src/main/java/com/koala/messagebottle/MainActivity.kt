package com.koala.messagebottle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.koala.messagebottle.login.LoginActivity

private const val TAG = "MainActivity"
private const val REQUEST_CODE_LOGIN = 100

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        // TODO: re-evaluate
        // We don't care about google signin State, we care more about being signed in to OUR app
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (account == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LOGIN)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            REQUEST_CODE_LOGIN -> {
                handleLogin(resultCode)
            }

            else -> Log.w(TAG, "Unhandled request code")
        }
    }

    private fun handleLogin(resultCode: Int) {
        if (Activity.RESULT_OK == resultCode) {
            Log.i(TAG, "Login is successful")
        } else {
            Log.w(TAG, "Login has failed, killing app")
            finish()
        }
    }
}
