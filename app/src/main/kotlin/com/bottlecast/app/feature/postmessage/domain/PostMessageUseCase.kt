package com.bottlecast.app.feature.postmessage.domain

import com.bottlecast.app.common.authentication.domain.IAuthenticationRepository
import com.bottlecast.app.common.authentication.domain.UserEntity
import com.bottlecast.app.common.messages.domain.IMessageRepository
import com.bottlecast.app.common.messages.domain.MessageEntity
import timber.log.Timber
import javax.inject.Inject

const val MINIMUM_MESSAGE_LENGTH: Int = 10

class PostMessageUseCase @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository,
    private val messageRepository: IMessageRepository
) {

    suspend fun postMessage(message: String): PostMessageResult {
        Timber.i("[postMessage] Initiating post of message to datasource")

        val trimmedMessage = message.trim()
        if (trimmedMessage.length < MINIMUM_MESSAGE_LENGTH) {
            Timber.e("[postMessage] Message is too short, please enter a message with at least $MINIMUM_MESSAGE_LENGTH characters")
            return PostMessageResult.InsufficientLength
        }

        return when (val user: UserEntity = authenticationRepository.user.value) {
            is UserEntity.AuthenticatedUser -> {
                val messageEntity = MessageEntity(trimmedMessage, user.userId)
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

    data object InsufficientLength : PostMessageResult()
}