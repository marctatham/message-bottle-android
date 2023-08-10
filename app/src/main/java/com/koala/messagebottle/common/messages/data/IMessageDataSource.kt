package com.koala.messagebottle.common.messages.data

interface IMessageDataSource {

    suspend fun getMessage(): MessageDataModel

    suspend fun getMessages(): List<MessageDataModel>

    suspend fun postMessage(message: MessageDataModel)

}