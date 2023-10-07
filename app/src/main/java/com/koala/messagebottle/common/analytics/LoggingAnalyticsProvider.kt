package com.koala.messagebottle.common.analytics

import android.os.Bundle
import timber.log.Timber

// TODO: let's make sure this is plugged into Debug builds
class LoggingAnalyticsProvider : ITracker {

    override fun trackScreen(screenName: String) {
        Timber.i("Screen: $screenName")
    }

    override fun trackEvent(analyticsEvent: AnalyticsEvent, params: Map<String, String>?) {
        val eventParams: Bundle? = params?.let {
            Bundle().apply {
                it.forEach { (key, value) ->
                    putString(key, value)
                }
            }
        }

        Timber.i("Event: ${analyticsEvent.eventName} with params: $eventParams")
    }
}
