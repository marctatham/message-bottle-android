package com.koala.messagebottle.common.network.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object MoshiModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

}