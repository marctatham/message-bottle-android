package com.koala.messagebottle.di

import android.content.Context
import com.koala.messagebottle.BaseApplication
import com.koala.messagebottle.common.authentication.di.AuthenticationModule
import com.koala.messagebottle.common.messages.di.MessageModule
import com.koala.messagebottle.common.network.di.NetworkModule
import com.koala.messagebottle.home.di.HomeComponent
import com.koala.messagebottle.login.di.LoginComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppSubcomponents::class,

        // plumbing / infrastructure
        NetworkModule::class,

        // view model
        ViewModelBuilder::class,

        // common
        AuthenticationModule::class,
        MessageModule::class
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun loginComponent(): LoginComponent.Factory

    fun homeComponent(): HomeComponent.Factory

}