package com.koala.messagebottle


class Screen() {
    companion object {
        const val HOME: String = "home"
        const val GET_MESSAGES: String = "get_messages"
        const val POST_MESSAGE: String = "post_message"
        const val POST_MESSAGE_EDUCATIONAL: String = "post_message_login_required"
        const val VIEW_MESSAGES: String = "view_messages"
        const val LOGIN: String = "login"
    }
}

class Flow {
    companion object {
        const val POST_MESSAGE_FLOW: String = "post_message_flow"
    }
}
