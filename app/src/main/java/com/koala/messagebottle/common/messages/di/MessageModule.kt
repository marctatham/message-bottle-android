package com.koala.messagebottle.common.messages.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.common.messages.data.IMessageFirestoreSource
import com.koala.messagebottle.common.messages.data.IMessageDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MessageModule {

    @Provides
    @Singleton
    fun providesFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providesMessageService(firestore: FirebaseFirestore): IMessageDataSource {
        return IMessageFirestoreSource(firestore)
    }

}