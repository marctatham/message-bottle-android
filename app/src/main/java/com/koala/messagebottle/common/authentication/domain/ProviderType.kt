package com.koala.messagebottle.common.authentication.domain


sealed class ProviderType(val signInProvider: String) {

    companion object {
        const val PROVIDER_ANONYMOUS = "anonymous"
        const val PROVIDER_GOOGLE = "google.com"
    }

    object Anonymous : ProviderType(PROVIDER_ANONYMOUS)

    object Google : ProviderType(PROVIDER_GOOGLE)
}
