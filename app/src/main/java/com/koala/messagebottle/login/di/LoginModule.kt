package com.koala.messagebottle.login.di

import androidx.lifecycle.ViewModel
import com.koala.messagebottle.di.ViewModelBuilder
import com.koala.messagebottle.di.ViewModelKey
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for the tasks list feature.
 */
@Module
abstract class LoginModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun bindLoginActivity(): LoginActivity


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewmodel: LoginViewModel): ViewModel

}
