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
    private val mapper: MessageDataModelMapper,
    private val authenticationRepository: IAuthenticationRepository,
    private val dispatcherNetwork: CoroutineDispatcher
) : IMessageRepository {

    override suspend fun getMessage(): MessageEntity {
        Timber.v("Retrieving message from remote service")
        val messageDataModel = withContext(dispatcherNetwork) {
            messageDataSource.getMessage()
        }

        Timber.v("returning response ${Thread.currentThread().name}")
        return mapper.mapFrom(messageDataModel)
    }

    override suspend fun getMessages(): List<MessageEntity> {
        Timber.v("Retrieving ALL messages from remote service")
        val messageDataModels = withContext(dispatcherNetwork) { messageDataSource.getMessages() }
        return messageDataModels.map { mapper.mapFrom(it) }
    }

    override suspend fun postMessage(messageEntity: MessageEntity): PostMessageResult {
        Timber.i("[postMessage] Posting message")
        when (authenticationRepository.user.value) {
            is UserEntity.AuthenticatedUser -> {
                return withContext(dispatcherNetwork) {
                    val messageDataModel = mapper.mapTo(messageEntity)
                    messageDataSource.postMessage(messageDataModel)
                    Timber.i("[postMessage] Message posted successfully")
                    return@withContext PostMessageResult.Success
                }
            }

            UserEntity.UnauthenticatedUser -> {
                Timber.e("[postMessage] User is not authenticated, no posting allowed")
                return PostMessageResult.Unauthenticated
            }
        }
    }

}