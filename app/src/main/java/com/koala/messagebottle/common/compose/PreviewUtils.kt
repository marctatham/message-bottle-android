package com.koala.messagebottle.common.compose

import androidx.compose.runtime.Composable


val fakeClickHandler: () -> Unit = { println("I'm a fake function to satisfy jetpack compose preview requirements") }


val fakeChildComposable: @Composable () -> Unit = { println("I'm a fake function to satisfy jetpack compose preview requirements") }