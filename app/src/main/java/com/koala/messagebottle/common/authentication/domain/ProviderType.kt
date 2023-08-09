package com.koala.messagebottle.common.authentication.domain

sealed class ProviderType(val providerIdentifier: Int) {

    object Anonymous : ProviderType(0)

    object Google : ProviderType(1)
}