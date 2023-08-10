package com.koala.messagebottle.common.messages.data

import com.koala.messagebottle.common.messages.domain.IMessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class MessageRepository(
    private val messageDataSource: IMessageDataSource,
    private val mapper: MessageDataModelMapper,
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

    override suspend fun postMessage(messageEntity: MessageEntity) {
        Timber.v("Posting message to remote service")
        withContext(dispatcherNetwork) {
            val messageDataModel = mapper.mapTo(messageEntity)
            messageDataSource.postMessage(messageDataModel)
        }
    }

}