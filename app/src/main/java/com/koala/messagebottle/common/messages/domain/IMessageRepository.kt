package com.koala.messagebottle.common.messages.domain

interface IMessageRepository {

    suspend fun getMessage(): MessageEntity

    suspend fun getMessages(): List<MessageEntity>

    suspend fun postMessage(messageToPost: String): PostMessageResult

}

sealed class PostMessageResult {
    data class Success(val messageEntity: MessageEntity) : PostMessageResult()
    object Unauthenticated : PostMessageResult()
}