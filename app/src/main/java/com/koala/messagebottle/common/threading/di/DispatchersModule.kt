package com.koala.messagebottle.common.threading.di

import com.koala.messagebottle.common.threading.DispatcherDefault
import com.koala.messagebottle.common.threading.DispatcherIO
import com.koala.messagebottle.common.threading.DispatcherMain
import com.koala.messagebottle.common.threading.DispatcherUnconfined
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
    fun providesDispatcherIO() = Dispatchers.IO

    @Provides
    @DispatcherMain
    fun providesDispatcherMain() = Dispatchers.Main

    @Provides
    @DispatcherDefault
    fun providesDispatcherDefault() = Dispatchers.Default

    @Provides
    @DispatcherUnconfined
    fun providesDispatcherUnConfined() = Dispatchers.Unconfined
}
