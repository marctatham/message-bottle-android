package com.koala.messagebottle.common.messages.data

import android.util.Log
import com.koala.messagebottle.common.messages.domain.MessageEntity
import javax.inject.Inject

private const val TAG = "MessageRepository"

class MessageRepository @Inject constructor(
    private val messageService: MessageService,
    private val mapper: MessageDataModelMapper
) {

    suspend fun getMessage(): MessageEntity {
        Log.v(TAG, "Retrieving message from remote service")
        val messageDataModel = messageService.getMessage()
        return mapper.mapFrom(messageDataModel)
    }

    suspend fun getMessages(): List<MessageEntity> {
        Log.v(TAG, "Retrieving ALL messages from remote service")
        val messageDataModels = messageService.getMessages()
        return messageDataModels.map { mapper.mapFrom(it) }
    }

    suspend fun postMessage(messageEntity: MessageEntity) {
        Log.v(TAG, "Posting message to remote service")

        val messageDataModel = mapper.mapTo(messageEntity)
        messageService.postMessage(messageDataModel)
    }

}