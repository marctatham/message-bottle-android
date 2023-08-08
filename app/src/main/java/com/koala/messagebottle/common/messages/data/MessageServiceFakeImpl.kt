package com.koala.messagebottle.common.messages.data

import kotlin.random.Random

// TODO: Let's create firebase-backed implementation
class MessageServiceFakeImpl() : MessageService {

    private val messages = ArrayList<MessageDataModel>()

    init {
        messages.add(MessageDataModel("Embrace the chaos – it's just a symphony of cosmic giggles playing in the key of life."))
        messages.add(MessageDataModel("A wise person knows that a cookie in each hand doubles the fortune."))
        messages.add(MessageDataModel("Your path to success is paved with mismatched socks and spontaneous dance breaks."))
    }

    override suspend fun getMessage(): MessageDataModel {
        return messages.elementAt(Random.nextInt(messages.size))
    }

    override suspend fun getMessages(): List<MessageDataModel> = messages


    override suspend fun postMessage(message: MessageDataModel) {
        messages.add(message)
    }
}