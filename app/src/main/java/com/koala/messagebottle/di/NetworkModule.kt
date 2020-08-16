package com.koala.messagebottle.di

import com.koala.messagebottle.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

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

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        // TODO: this should be determined based on the flavour
        val baseUrl = "https://bottling-messages.nw.r.appspot.com"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

}