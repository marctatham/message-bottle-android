package com.koala.messagebottle.common.authentication.domain

sealed class UserEntity {

    data class AuthenticatedUser(
        val providerType: ProviderType,
        val jwtToken: String
    ) : UserEntity()

    object UnauthenticatedUser : UserEntity()
}
