package com.koala.messagebottle.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.koala.messagebottle.BaseApplication
import com.koala.messagebottle.R
import com.koala.messagebottle.getmessage.GetMessageFragment
import com.koala.messagebottle.home.di.HomeComponent
import com.koala.messagebottle.postmessage.PostMessageFragment
import com.koala.messagebottle.viewmessages.ViewMessagesFragment
import timber.log.Timber

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
            .addToBackStack("GetMessageFragment")
            .commit()
    }

    fun showPostMessage() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PostMessageFragment.newInstance())
            .addToBackStack("PostMessageFragment")
            .commit()
    }

    fun showViewMessages() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ViewMessagesFragment.newInstance())
            .addToBackStack("ViewMessagesFragment")
            .commit()
    }

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