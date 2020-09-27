package com.koala.messagebottle.common.messages.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageDataModel(
    @field:Json(name = "message") val message: String
)