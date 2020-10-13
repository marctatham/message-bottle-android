package com.koala.messagebottle.login.di

import androidx.fragment.app.FragmentManager
import com.koala.messagebottle.di.scope.ActivityScope
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.login.ThirdPartyLoginProvider
import com.koala.messagebottle.login.google.GoogleLoginProvider
import dagger.Module
import dagger.Provides

@Module
class ThirdPartyLoginProviderModule {

    @ActivityScope
    @Provides
    fun provideFragmentManager(loginActivity: LoginActivity): FragmentManager {
        return loginActivity.supportFragmentManager
    }

    @ActivityScope
    @Provides
    fun providesThirdPartyLoginProvider(fragmentManager: FragmentManager): ThirdPartyLoginProvider {
        return GoogleLoginProvider(fragmentManager)
    }

}