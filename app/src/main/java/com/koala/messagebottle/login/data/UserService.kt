package com.koala.messagebottle.login.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface UserService {

    @POST("user/")
    fun getCreateUser(@Body getCreateUserDataModel: GetCreateUserDataModel): Call<String>

    companion object {
        const val API_URL = "https://bottling-messages.nw.r.appspot.com/"
    }

}