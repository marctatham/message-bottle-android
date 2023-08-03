package com.koala.messagebottle.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.findNavController
import com.koala.messagebottle.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val TAG = "MainActivity"
private const val REQUEST_CODE_LOGIN = 100

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        homeComponent =
//            (application as Application).appComponent.homeComponent().create(this)
//        homeComponent.inject(this)

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
