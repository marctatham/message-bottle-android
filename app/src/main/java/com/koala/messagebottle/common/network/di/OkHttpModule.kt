package com.koala.messagebottle.common.network.di

import com.koala.messagebottle.BuildConfig
import com.koala.messagebottle.common.network.JwtAuthenticationInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object OkHttpModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(jwtAuthenticationInterceptor: JwtAuthenticationInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            addInterceptor(jwtAuthenticationInterceptor)
        }

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

}