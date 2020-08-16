package com.koala.messagebottle.login.di

import com.koala.messagebottle.login.LoginActivity
import dagger.Subcomponent

// Scope annotation that the LoginComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    // Factory to create instances of LoginComponent
    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    // Classes that can be injected by this Component
    fun inject(activity: LoginActivity)
}
