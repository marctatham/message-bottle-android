package com.koala.messagebottle

import android.app.Application
import com.koala.messagebottle.di.AppComponent
import com.koala.messagebottle.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeLogging()
    }

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }

    open fun initializeLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
