package com.koala.messagebottle.di

import com.koala.messagebottle.home.di.HomeComponent
import com.koala.messagebottle.login.di.LoginComponent
import dagger.Module


@Module(
    subcomponents = [
        HomeComponent::class,
        LoginComponent::class
    ]
)
class AppSubcomponents