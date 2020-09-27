package com.koala.messagebottle.common.messages.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageService {

    @GET("message/")
    suspend fun getMessage(): MessageDataModel

    @GET("messages/")
    suspend fun getMessages(): List<MessageDataModel>

    @POST("message/")
    suspend fun postMessage(@Body message: MessageDataModel)

}