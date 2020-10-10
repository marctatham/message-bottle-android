package com.koala.messagebottle.common.authentication.di

import com.koala.messagebottle.common.authentication.data.UserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class UserServiceModule {

    @Provides
    @Singleton
    fun providesUserService(
        retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }
}