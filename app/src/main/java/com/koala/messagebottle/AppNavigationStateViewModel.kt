package com.koala.messagebottle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppNavigationStateViewModel @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<AppState> = MutableStateFlow(AppState(false))
    val state: StateFlow<AppState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authenticationRepository.user.collect {
                when (it) {
                    is UserEntity.AuthenticatedUser -> _state.value = AppState(true)
                    UserEntity.UnauthenticatedUser -> _state.value = AppState(false)
                }
            }
        }
    }
}

data class AppState(val isAuthenticated: Boolean)
