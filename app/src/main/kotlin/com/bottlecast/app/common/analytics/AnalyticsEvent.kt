package com.bottlecast.app.common.analytics


sealed class AnalyticsEvent(val eventName: String) {
    data object GetMessageTapped : AnalyticsEvent("get_message_tapped")
    data object PostMessageTapped : AnalyticsEvent("post_message_tapped")
}
