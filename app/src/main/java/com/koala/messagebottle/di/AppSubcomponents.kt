package com.koala.messagebottle.di

import com.koala.messagebottle.login.di.LoginComponent
import dagger.Module


@Module(
    subcomponents = [
        LoginComponent::class
    ]
)
class AppSubcomponents