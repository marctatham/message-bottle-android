package com.koala.messagebottle.common.data.authentication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCreateUserResponseDataModel(
    @field:Json(name = "jwtToken") val jwtToken: String,
    @field:Json(name = "authProvider") val authProvider: Int
)