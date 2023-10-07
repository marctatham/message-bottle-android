package com.bottlecast.app.common.messages.domain

interface IMessageRepository {

    suspend fun getMessage(): MessageEntity

    suspend fun getMessages(): List<MessageEntity>

    suspend fun postMessage(message: MessageEntity)

}
