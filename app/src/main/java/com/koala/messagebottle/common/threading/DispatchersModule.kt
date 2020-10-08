package com.koala.messagebottle.common.threading

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
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
