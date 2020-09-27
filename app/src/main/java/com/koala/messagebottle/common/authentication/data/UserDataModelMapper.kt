package com.koala.messagebottle.common.authentication.data

import com.koala.messagebottle.common.authentication.domain.AuthenticationProvider
import com.koala.messagebottle.common.authentication.domain.UserEntity
import javax.inject.Inject

class UserDataModelMapper @Inject constructor() {

    fun map(userDataModel: GetCreateUserResponseDataModel): UserEntity.LoggedInUser =
        when (userDataModel.authProvider) {
            1 -> UserEntity.LoggedInUser(AuthenticationProvider.Google)
            else -> throw IllegalArgumentException("")
        }
}