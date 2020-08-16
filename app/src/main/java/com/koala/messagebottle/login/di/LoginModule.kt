package com.koala.messagebottle.login.di

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.koala.messagebottle.di.ViewModelBuilder
import com.koala.messagebottle.di.ViewModelKey
import com.koala.messagebottle.login.GoogleLoginProvider
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.login.LoginViewModel
import com.koala.messagebottle.login.ThirdPartyLoginProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(
    includes = [
        ThirdPartyLoginProviderModule::class
    ]
)
abstract class LoginModule {

//    @Binds
//    @IntoMap
//    @ViewModelKey(LoginViewModel::class)
//    abstract fun bindViewModel(viewmodel: LoginViewModel): ViewModel


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

@Module
object ThirdPartyLoginProviderModule {

    //@ActivityScope
    @Provides
    fun provideFragmentManager(loginActivity: LoginActivity): FragmentManager {
        return loginActivity.supportFragmentManager
    }

    //@ActivityScope
    @Provides
    fun providesThirdPartyLoginProvider(fragmentManager: FragmentManager): ThirdPartyLoginProvider {
        return GoogleLoginProvider(fragmentManager)
    }

}