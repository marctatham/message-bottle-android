package com.bottlecast.app.common.authentication.domain


sealed class AuthProviderType(val signInProvider: String) {

    companion object {
        const val PROVIDER_ANONYMOUS = "anonymous"
        const val PROVIDER_GOOGLE = "google.com"
    }

    data object Anonymous : AuthProviderType(PROVIDER_ANONYMOUS)

    data object Google : AuthProviderType(PROVIDER_GOOGLE)
}
