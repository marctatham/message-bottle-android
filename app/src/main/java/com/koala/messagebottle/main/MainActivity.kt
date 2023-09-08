package com.koala.messagebottle.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.navigation.findNavController
import com.koala.messagebottle.R
import com.koala.messagebottle.main.Constants.REQUEST_CODE_LOGIN
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        // TODO: introduce Theme at top most level
        // for this, this activity we want to be the single entry point into the application (at least in context of THIS app)

        //setContent {
        // TODO: use our BottlingTheme of course... this is to jog the ol' memory
        //            ReplyTheme {
        //                val windowSize = calculateWindowSizeClass(this)
        //                val displayFeatures = calculateDisplayFeatures(this)
        //                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        //
        //                ReplyApp(
        //                    windowSize = windowSize,
        //                    displayFeatures = displayFeatures,
        //                    replyHomeUIState = uiState,
        //                    closeDetailScreen = {
        //                        viewModel.closeDetailScreen()
        //                    },
        //                    navigateToDetail = { emailId, pane ->
        //                        viewModel.setOpenedEmail(emailId, pane)
        //                    },
        //                    toggleSelectedEmail = { emailId ->
        //                        viewModel.toggleSelectedEmail(emailId)
        //                    }
        //                )
        //            }
        //        }

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
