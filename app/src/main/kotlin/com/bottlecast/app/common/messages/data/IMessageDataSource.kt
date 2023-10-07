package com.bottlecast.app.common.messages.data

import com.bottlecast.app.common.messages.domain.MessageEntity

interface IMessageDataSource {

    suspend fun getMessage(): MessageEntity

    suspend fun getMessages(): List<MessageEntity>

    suspend fun postMessage(message: MessageEntity)

}