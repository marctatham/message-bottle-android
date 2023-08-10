package com.koala.messagebottle.common.authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.common.authentication.data.AuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.threading.DispatcherIO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesAuthenticationRepository(
        firebaseAuth: FirebaseAuth,
        @DispatcherIO dispatcher: CoroutineDispatcher
    ): IAuthenticationRepository =
        AuthenticationRepository(
            firebaseAuth,
            dispatcher
        )
}