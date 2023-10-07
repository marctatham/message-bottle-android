package com.bottlecast.app.common.messages.data.mapper

import com.bottlecast.app.common.messages.data.model.MessageFirestoreDataModel
import com.bottlecast.app.common.messages.domain.MessageEntity
import javax.inject.Inject

class MessageDataModelMapper @Inject constructor() {

    fun mapFrom(messageDataModel: MessageFirestoreDataModel): MessageEntity =
        MessageEntity(messageDataModel.messageContent, messageDataModel.userId)
}