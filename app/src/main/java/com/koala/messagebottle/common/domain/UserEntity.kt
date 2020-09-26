package com.koala.messagebottle.common.domain

// for now a logged in user doesn't actually provide any additional functionality but since
// this is destined to change in the near future, it's being integrated as a 1st class citizen
sealed class UserEntity {

    data class LoggedInUser(
        val authenticationProvider: AuthenticationProvider
    ) : UserEntity()

    object Anonymous : UserEntity()

}