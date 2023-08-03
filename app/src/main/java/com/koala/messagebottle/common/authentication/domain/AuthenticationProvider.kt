package com.koala.messagebottle.common.authentication.domain

sealed class AuthenticationProvider(val providerIdentifier: Int) {

    object Anonymous : AuthenticationProvider(0)

    object Google : AuthenticationProvider(1)
}