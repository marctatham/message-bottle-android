package com.koala.messagebottle.di

import android.content.Context
import com.koala.messagebottle.BaseApplication
import com.koala.messagebottle.login.di.LoginComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        NetworkModule::class,
        AuthenticationModule::class,
        SubcomponentsModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun loginComponent(): LoginComponent.Factory

}


@Module(
    subcomponents = [
        LoginComponent::class
    ]
)
object SubcomponentsModule