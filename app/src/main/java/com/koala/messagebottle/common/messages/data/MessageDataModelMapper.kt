package com.koala.messagebottle.common.messages.data

import com.koala.messagebottle.common.messages.domain.MessageEntity
import javax.inject.Inject

class MessageDataModelMapper @Inject constructor() {

    fun mapFrom(messageDataModel: MessageDataModel): MessageEntity =
        MessageEntity(messageDataModel.message)

    fun mapTo(messageEntity: MessageEntity): MessageDataModel =
        MessageDataModel(messageEntity.message)
}