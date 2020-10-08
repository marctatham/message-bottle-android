package com.koala.messagebottle.common.authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.common.authentication.data.UserService
import com.koala.messagebottle.common.authentication.data.firebase.FirebaseAuthenticator
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object AuthenticationModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun providesFirebaseAuthenticator(firebaseAuth: FirebaseAuth) =
        FirebaseAuthenticator(firebaseAuth)

    @Provides
    @Singleton
    fun providesUserService(
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

}