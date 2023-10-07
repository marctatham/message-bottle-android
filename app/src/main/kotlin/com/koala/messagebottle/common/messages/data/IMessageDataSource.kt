package com.koala.messagebottle.common.messages.data

import com.koala.messagebottle.common.messages.domain.MessageEntity

interface IMessageDataSource {

    suspend fun getMessage(): MessageEntity

    suspend fun getMessages(): List<MessageEntity>

    suspend fun postMessage(message: MessageEntity)

}