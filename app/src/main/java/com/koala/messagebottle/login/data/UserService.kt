package com.koala.messagebottle.login.data

import retrofit2.http.Body
import retrofit2.http.POST


interface UserService {

    @POST("user/")
    suspend fun getCreateUser(@Body getCreateUserDataModel: GetCreateUserDataModel): GetCreateUserResponseDataModel

    companion object {
        const val API_URL = "https://bottling-messages.nw.r.appspot.com/"
    }

}