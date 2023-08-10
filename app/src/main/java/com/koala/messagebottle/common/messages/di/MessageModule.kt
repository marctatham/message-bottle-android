package com.koala.messagebottle.common.messages.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.messages.data.IMessageDataSource
import com.koala.messagebottle.common.messages.data.mapper.MessageDataModelMapper
import com.koala.messagebottle.common.messages.data.MessageFirestoreSource
import com.koala.messagebottle.common.messages.data.MessageRepository
import com.koala.messagebottle.common.messages.domain.IMessageRepository
import com.koala.messagebottle.common.threading.DispatcherIO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MessageModule {

    @Provides
    @Singleton
    fun providesFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesMessageService(firestore: FirebaseFirestore, mapper: MessageDataModelMapper): IMessageDataSource {
        return MessageFirestoreSource(firestore, mapper)
    }

    @Provides
    @Singleton
    fun providesMessageRepository(
        messageDataSource: IMessageDataSource,
        authenticationRepository: IAuthenticationRepository,
        @DispatcherIO dispatcher: CoroutineDispatcher
    ): IMessageRepository {
        return MessageRepository(
            messageDataSource,
            authenticationRepository,
            dispatcher
        )
    }
}