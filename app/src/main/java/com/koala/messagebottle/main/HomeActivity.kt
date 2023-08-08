package com.koala.messagebottle.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.koala.messagebottle.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val REQUEST_CODE_LOGIN = 100

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
    }

    fun showGetMessage() = findNavController(R.id.nav_host_fragment)
        .navigate(R.id.action_homeToGetMessage)

    fun showPostMessage() = findNavController(R.id.nav_host_fragment)
        .navigate(R.id.action_homeToPostMessage)

    fun showViewMessages() = findNavController(R.id.nav_host_fragment)
        .navigate(R.id.action_homeToVewMessages)

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            REQUEST_CODE_LOGIN -> {
                handleLogin(resultCode)
            }

            else -> Timber.w("Unhandled request code")
        }
    }

    private fun handleLogin(resultCode: Int) {
        if (Activity.RESULT_OK == resultCode) {
            Timber.i("Login is successful")
        } else {
            Timber.w("Login has failed, killing app")
        }
    }
}
