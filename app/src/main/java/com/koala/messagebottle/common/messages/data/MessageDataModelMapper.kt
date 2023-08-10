package com.koala.messagebottle.common.messages.data

import com.koala.messagebottle.common.messages.data.model.MessageFirestoreDataModel
import com.koala.messagebottle.common.messages.domain.MessageEntity
import javax.inject.Inject

class MessageDataModelMapper @Inject constructor() {

    fun mapFrom(messageDataModel: MessageFirestoreDataModel): MessageEntity =
        MessageEntity(messageDataModel.messageContent, messageDataModel.userId)
}