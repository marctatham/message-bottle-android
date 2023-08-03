package com.koala.messagebottle.common.threading

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @DispatcherIO
    @JvmStatic
    fun providesDispatcherIO() = Dispatchers.IO

    @Provides
    @DispatcherMain
    @JvmStatic
    fun providesDispatcherMain() = Dispatchers.Main

    @Provides
    @DispatcherDefault
    @JvmStatic
    fun providesDispatcherDefault() = Dispatchers.Default

    @Provides
    @DispatcherUnconfined
    @JvmStatic
    fun providesDispatcherUnConfined() = Dispatchers.Unconfined
}
