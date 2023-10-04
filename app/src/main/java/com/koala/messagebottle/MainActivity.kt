package com.koala.messagebottle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.koala.messagebottle.common.analytics.IAnalyticsProvider
import com.koala.messagebottle.common.analytics.LocalTracker
import com.koala.messagebottle.common.ui.BottlingTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsProvider: IAnalyticsProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BottlingTheme {
                // ensure that the analytics provider as has been configured
                // via Dagger Hilt is made available to the entire app
                CompositionLocalProvider(LocalTracker provides analyticsProvider) {
                    MessageInABottleApp(analyticsProvider)
                }
            }
        }
    }
}
