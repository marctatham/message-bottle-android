package com.koala.messagebottle.common.authentication.di

import com.koala.messagebottle.common.authentication.data.jwt.IJwtTokenProvider
import com.koala.messagebottle.common.authentication.data.jwt.JwtTokenManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class JwtTokenProviderModule {

    @Provides
    @Singleton
    fun providesJwtTokenProvider(
        jwtTokenManager: JwtTokenManager
    ): IJwtTokenProvider = jwtTokenManager

}