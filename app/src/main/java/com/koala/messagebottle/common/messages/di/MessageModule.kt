package com.koala.messagebottle.common.messages.di

import com.koala.messagebottle.common.messages.data.MessageService
import com.koala.messagebottle.common.messages.data.MessageServiceFakeImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MessageModule {

    @Provides
    @Singleton
    fun providesMessageService(): MessageService {
        return MessageServiceFakeImpl()
    }

}