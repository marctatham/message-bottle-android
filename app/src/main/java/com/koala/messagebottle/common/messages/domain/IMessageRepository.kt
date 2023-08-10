package com.koala.messagebottle.common.messages.domain

interface IMessageRepository {

    suspend fun getMessage(): MessageEntity

    suspend fun getMessages(): List<MessageEntity>

    suspend fun postMessage(messageEntity: MessageEntity)

}