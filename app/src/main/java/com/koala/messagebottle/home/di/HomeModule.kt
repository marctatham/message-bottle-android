package com.koala.messagebottle.home.di

import androidx.lifecycle.ViewModel
import com.koala.messagebottle.di.ViewModelBuilder
import com.koala.messagebottle.di.ViewModelKey
import com.koala.messagebottle.getmessage.GetMessageViewModel
import com.koala.messagebottle.home.HomeActivity
import com.koala.messagebottle.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun bindHomeActivity(): HomeActivity

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GetMessageViewModel::class)
    abstract fun bindGetMessageViewModel(viewModel: GetMessageViewModel): ViewModel

}