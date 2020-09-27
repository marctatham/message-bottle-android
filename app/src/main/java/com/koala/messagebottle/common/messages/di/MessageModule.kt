package com.koala.messagebottle.common.messages.di

import com.koala.messagebottle.common.messages.data.MessageDataModelMapper
import com.koala.messagebottle.common.messages.data.MessageRepository
import com.koala.messagebottle.common.messages.data.MessageService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class MessageModule {

    @Provides
    @Singleton
    fun providesMessageService(retrofit: Retrofit): MessageService {
        return retrofit.create(MessageService::class.java)
    }

    @Provides
    @Singleton
    fun providesMessageRepository(
        messageService: MessageService,
        messageDataModelMapper: MessageDataModelMapper
    ): MessageRepository {
        return MessageRepository(messageService, messageDataModelMapper)
    }

}