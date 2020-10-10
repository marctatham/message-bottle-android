package com.koala.messagebottle.common.authentication.di

import com.koala.messagebottle.common.authentication.data.jwt.IJwtTokenPersister
import com.koala.messagebottle.common.authentication.data.jwt.JwtTokenManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class JwtTokenPersisterModule {

    @Provides
    @Singleton
    fun providesJwtTokenPersister(
        jwtTokenManager: JwtTokenManager
    ): IJwtTokenPersister = jwtTokenManager

}