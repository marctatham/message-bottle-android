package com.koala.messagebottle.login.di

import com.koala.messagebottle.di.scope.ActivityScope
import com.koala.messagebottle.login.LoginActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        LoginModule::class,
        ThirdPartyLoginProviderModule::class
    ]
)
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: LoginActivity): LoginComponent
    }

    fun inject(activity: LoginActivity)
}
