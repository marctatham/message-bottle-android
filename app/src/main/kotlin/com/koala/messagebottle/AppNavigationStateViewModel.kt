package com.koala.messagebottle

import androidx.lifecycle.ViewModel
import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppNavigationStateViewModel @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository,
) : ViewModel() {

    fun onNavigateToPostMessage(): String {
        return if (authenticationRepository.user.value is UserEntity.AuthenticatedUser) {
            Screen.POST_MESSAGE
        } else {
            Screen.POST_MESSAGE_EDUCATIONAL
        }
    }
}
