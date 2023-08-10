package com.koala.messagebottle.common.messages.data

import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.UserEntity
import com.koala.messagebottle.common.messages.domain.IMessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import com.koala.messagebottle.common.messages.domain.PostMessageResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class MessageRepository(
    private val messageDataSource: IMessageDataSource,
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatcherNetwork: CoroutineDispatcher
) : IMessageRepository {

    override suspend fun getMessage(): MessageEntity {
        Timber.v("[getMessage] Retrieving message from datasource")
        return withContext(dispatcherNetwork) {
            return@withContext messageDataSource.getMessage()
        }
    }

    override suspend fun getMessages(): List<MessageEntity> {
        Timber.v("[getMessages] Retrieving ALL messages from datasource")
        return withContext(dispatcherNetwork) {
            return@withContext messageDataSource.getMessages()
        }
    }

    override suspend fun postMessage(messageToPost: String): PostMessageResult {
        Timber.i("[postMessage] Posting message to datasource")
        when (val user: UserEntity = authenticationRepository.user.value) {
            is UserEntity.AuthenticatedUser -> {
                return withContext(dispatcherNetwork) {
                    val messageEntity = MessageEntity(messageToPost, user.userId)
                    messageDataSource.postMessage(messageEntity)
                    Timber.i("[postMessage] Message posted successfully")
                    return@withContext PostMessageResult.Success(messageEntity)
                }
            }

            UserEntity.UnauthenticatedUser -> {
                Timber.e("[postMessage] User is not authenticated, no posting allowed")
                return PostMessageResult.Unauthenticated
            }
        }
    }

}