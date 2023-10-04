package com.koala.messagebottle.common.analytics

interface IAnalyticsProvider {

    fun trackScreen(screenName: String)

    fun trackEvent(analyticsEvent: AnalyticsEvent, params: Map<String, String>? = null)
}
