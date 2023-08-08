package com.koala.messagebottle.main.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    // TODO: Implement the ViewModel

    fun test(): Unit {
        println("This is a test")
    }
}
