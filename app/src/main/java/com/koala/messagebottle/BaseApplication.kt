package com.koala.messagebottle

import android.app.Application
import com.koala.messagebottle.di.AppComponent
import com.koala.messagebottle.di.DaggerAppComponent


open class BaseApplication : Application() {

//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return DaggerApplicationComponent.factory().create(applicationContext)
//    }

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }


//    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//        return appComponent
//    }


//    val appComponent: ApplicationComponent by lazy {
//        initializeComponent()
//    }
//
//    open fun initializeComponent(): ApplicationComponent {
//        // Creates an instance of AppComponent using its Factory constructor
//        // We pass the applicationContext that will be used as Context in the graph
//        return DaggerApplicationComponent.factory().create(applicationContext)
//    }
}
