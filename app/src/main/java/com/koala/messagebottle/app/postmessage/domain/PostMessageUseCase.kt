package com.koala.messagebottle.app.postmessage.domain

import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.UserEntity
import com.koala.messagebottle.common.messages.domain.IMessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import timber.log.Timber
import javax.inject.Inject

class PostMessageUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository,
    private val messageRepository: IMessageRepository
) {

    suspend fun postMessage(message: String): PostMessageResult {
        Timber.i("[postMessage] Initiating post of message to datasource")
        return when (val user: UserEntity = authenticationRepository.user.value) {
            is UserEntity.AuthenticatedUser -> {
                val messageEntity = MessageEntity(message, user.userId)
                messageRepository.postMessage(messageEntity)
                Timber.i("[postMessage] Message posted successfully")
                PostMessageResult.Success(messageEntity)

            }

            UserEntity.UnauthenticatedUser -> {
                Timber.e("[postMessage] User is not authenticated, please authenticate to post a message")
                PostMessageResult.Unauthenticated
            }
        }
    }
}

sealed class PostMessageResult {
    data class Success(val message: MessageEntity) : PostMessageResult()

    data object Unauthenticated : PostMessageResult()
}