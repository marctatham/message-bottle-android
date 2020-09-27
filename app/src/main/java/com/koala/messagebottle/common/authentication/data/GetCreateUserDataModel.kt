package com.koala.messagebottle.common.authentication.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCreateUserDataModel(
    @field:Json(name = "idToken") val myId: String
)