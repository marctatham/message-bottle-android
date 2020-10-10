package com.koala.messagebottle.common.network.di

import com.koala.messagebottle.common.authentication.di.JwtTokenProviderModule
import com.koala.messagebottle.common.authentication.di.UserServiceModule
import com.koala.messagebottle.common.messages.di.MessageModule
import dagger.Module

@Module(
    includes = [
        // serialisation, networking
        MoshiModule::class, OkHttpModule::class, RetrofitModule::class,

        // jwt token provider
        JwtTokenProviderModule::class,

        // retrofit services
        MessageModule::class, UserServiceModule::class]
)
object NetworkModule