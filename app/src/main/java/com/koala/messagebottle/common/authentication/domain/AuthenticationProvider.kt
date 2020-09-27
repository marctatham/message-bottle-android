package com.koala.messagebottle.common.authentication.domain

sealed class AuthenticationProvider(val providerIdentifier: Int) {

    object Google : AuthenticationProvider(1)
}