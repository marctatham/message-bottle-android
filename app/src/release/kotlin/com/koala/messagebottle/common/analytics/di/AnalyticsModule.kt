package com.koala.messagebottle.common.analytics.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.common.analytics.FirebaseTracker
import com.koala.messagebottle.common.analytics.ITracker
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
    fun providesFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

    @Provides
    @Singleton
    fun providesAnalyticsProvider(
        firebaseAnalytics: FirebaseAnalytics
    ): ITracker = FirebaseTracker(firebaseAnalytics)

}
