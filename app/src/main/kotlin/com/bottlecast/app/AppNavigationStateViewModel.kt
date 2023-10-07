package com.bottlecast.app

import androidx.lifecycle.ViewModel
import com.bottlecast.app.common.authentication.domain.IAuthenticationRepository
import com.bottlecast.app.common.authentication.domain.UserEntity
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
