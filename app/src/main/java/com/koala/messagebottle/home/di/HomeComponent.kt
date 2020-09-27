package com.koala.messagebottle.home.di

import com.koala.messagebottle.di.scope.ActivityScope
import com.koala.messagebottle.getmessage.GetMessageFragment
import com.koala.messagebottle.home.HomeActivity
import com.koala.messagebottle.home.HomeFragment
import com.koala.messagebottle.postmessage.PostMessageFragment
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules = [
        HomeModule::class
    ]
)
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance homeActivity: HomeActivity): HomeComponent
    }

    fun inject(homeFragment: HomeFragment)
    fun inject(getMessageFragment: GetMessageFragment)
    fun inject(postMessageFragment: PostMessageFragment)
}
