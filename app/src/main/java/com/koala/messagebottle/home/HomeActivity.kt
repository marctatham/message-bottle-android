package com.koala.messagebottle.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.koala.messagebottle.BaseApplication
import com.koala.messagebottle.R
import com.koala.messagebottle.getmessage.GetMessageFragment
import com.koala.messagebottle.home.di.HomeComponent

private const val TAG = "MainActivity"
private const val REQUEST_CODE_LOGIN = 100

class HomeActivity : AppCompatActivity() {

    lateinit var homeComponent: HomeComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        homeComponent = (application as BaseApplication).appComponent.homeComponent().create(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commit()
        }
    }

    fun showGetMessage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, GetMessageFragment.newInstance())
            .commit()
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
        }
    }
}
