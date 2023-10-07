package com.bottlecast.app.common.authentication.domain

sealed class UserEntity {

    data class AuthenticatedUser(
        val providerType: AuthProviderType,
        val jwtToken: String,
        val userId: String,
        val isAdmin: Boolean
    ) : UserEntity()

    data object UnauthenticatedUser : UserEntity()
}
