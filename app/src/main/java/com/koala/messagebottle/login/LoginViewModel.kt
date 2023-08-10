package com.koala.messagebottle.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the Details screen.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationRepository: IAuthenticationRepository
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Anonymous)
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            authenticationRepository.user.collect {
                when (it) {
                    is UserEntity.AuthenticatedUser -> _state.value = State.LoggedInUser
                    UserEntity.UnauthenticatedUser -> _state.value = State.Anonymous
                }
            }

        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "There was a problem signing into your account")
        _state.value = State.Failure
    }


    fun initiateLoginWithGoogle(idToken: String) = viewModelScope.launch(exceptionHandler) {
        _state.value = State.Loading
        authenticationRepository.firebaseAuthWithGoogle(idToken)
    }

    fun initiateAnonymousLogin() = viewModelScope.launch(exceptionHandler) {
        _state.value = State.Loading
        authenticationRepository.signInAnonymously()

    }

    fun initiateSignOut() = viewModelScope.launch {
        _state.value = State.Loading
        authenticationRepository.signOut()
    }
}

// the different states the login screen can be in
sealed class State {

    object Anonymous : State()

    object Loading : State()

    object LoggedInUser : State()

    object Failure : State()
}
