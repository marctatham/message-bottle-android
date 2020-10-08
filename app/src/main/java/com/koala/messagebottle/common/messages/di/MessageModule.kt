package com.koala.messagebottle.common.messages.di

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

}