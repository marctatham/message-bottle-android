package com.koala.messagebottle.common.data.authentication

import com.koala.messagebottle.common.domain.AuthenticationProvider
import com.koala.messagebottle.common.domain.UserEntity
import javax.inject.Inject

class UserDataModelMapper @Inject constructor() {

    fun map(userDataModel: GetCreateUserResponseDataModel): UserEntity =
        when (userDataModel.authProvider) {
            1 -> UserEntity(AuthenticationProvider.Google)
            else -> throw IllegalArgumentException("")
        }
}