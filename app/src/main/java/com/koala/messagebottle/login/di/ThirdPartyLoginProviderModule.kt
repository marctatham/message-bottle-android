package com.koala.messagebottle.login.di

import androidx.fragment.app.FragmentManager
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.login.ThirdPartyLoginProvider
import com.koala.messagebottle.login.google.GoogleLoginProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class ThirdPartyLoginProviderModule {

    @Provides
    @ActivityRetainedScoped
    fun provideFragmentManager(loginActivity: LoginActivity): FragmentManager {
        return loginActivity.supportFragmentManager
    }

    @Provides
    @ActivityRetainedScoped
    fun providesThirdPartyLoginProvider(fragmentManager: FragmentManager): ThirdPartyLoginProvider {
        return GoogleLoginProvider(fragmentManager)
    }

}