package com.koala.messagebottle.login.di

import androidx.fragment.app.FragmentManager
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.login.ThirdPartyLoginProvider
import com.koala.messagebottle.login.google.GoogleLoginProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ThirdPartyLoginProviderModule {

//    @ViewModelScoped
    @Provides
    fun provideFragmentManager(loginActivity: LoginActivity): FragmentManager {
        return loginActivity.supportFragmentManager
    }

//    @ActivityScoped
    @Provides
    fun providesThirdPartyLoginProvider(fragmentManager: FragmentManager): ThirdPartyLoginProvider {
        return GoogleLoginProvider(fragmentManager)
    }

}