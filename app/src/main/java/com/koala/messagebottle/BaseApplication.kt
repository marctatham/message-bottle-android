package com.koala.messagebottle

import android.app.Application
import com.koala.messagebottle.di.AppComponent
import com.koala.messagebottle.di.DaggerAppComponent


open class BaseApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}
