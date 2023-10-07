package com.bottlecast.app.common.messages.data

import com.bottlecast.app.common.messages.domain.IMessageRepository
import com.bottlecast.app.common.messages.domain.MessageEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class MessageRepository(
    private val messageDataSource: IMessageDataSource,
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

    override suspend fun postMessage(message: MessageEntity) {
        Timber.i("[postMessage] Posting message to datasource")
        withContext(dispatcherNetwork) {
            messageDataSource.postMessage(message)
            Timber.i("[postMessage] Message posted successfully")
        }
    }
}
