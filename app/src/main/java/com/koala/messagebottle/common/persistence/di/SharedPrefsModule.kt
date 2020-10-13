package com.koala.messagebottle.common.persistence.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

private const val FILE_NAME = "ApplicationPreferences"

@Module
class SharedPrefsModule {

    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

}