package com.koala.messagebottle.common.domain

sealed class AuthenticationProvider(val providerIdentifier: Int) {

    object Google : AuthenticationProvider(1)
}