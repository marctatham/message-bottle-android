package com.koala.messagebottle.common.analytics.di

import com.koala.messagebottle.common.analytics.ITracker
import com.koala.messagebottle.common.analytics.LoggingTracker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    @Singleton
    fun providesAnalyticsProvider(): ITracker = LoggingTracker()

}
