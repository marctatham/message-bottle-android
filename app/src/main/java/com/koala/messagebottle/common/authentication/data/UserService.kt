package com.koala.messagebottle.common.authentication.data

import retrofit2.http.Body
import retrofit2.http.POST


interface UserService {

    @POST("user")
    suspend fun getCreateUser(@Body getCreateUserDataModel: GetCreateUserDataModel): GetCreateUserResponseDataModel

}