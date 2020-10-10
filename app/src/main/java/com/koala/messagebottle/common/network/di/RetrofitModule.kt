package com.koala.messagebottle.common.network.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

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