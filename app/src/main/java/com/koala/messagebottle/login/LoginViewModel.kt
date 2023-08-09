package com.koala.messagebottle.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.authentication.domain.AuthenticationProvider
import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
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

    private val _state: MutableLiveData<State> = MutableLiveData(getCurrentState())
    val state: LiveData<State> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "There was a problem signing into your account")
        _state.value = State.Failure
    }

    // TODO: let's revisit how much sense this makes here
    // perhaps this makes more sense to keep at the periphery of the architecture,
    // and only upon completion does it then interact with our authentication repo
    fun initiateLoginWithGoogle(idToken: String) = viewModelScope.launch(exceptionHandler) {
        _state.value = State.Loading
        _state.value = authenticationRepository.firebaseAuthWithGoogle(idToken)
            .toState()
    }

    fun initiateAnonymousLogin() = viewModelScope.launch(exceptionHandler) {
        _state.value = State.Loading
        _state.value = authenticationRepository
            .signInAnonymously()
            .toState()
    }

    fun initiateSignOut() = viewModelScope.launch {
        _state.value = State.Loading
        authenticationRepository.signOut()
        _state.value = getCurrentState()
    }

    // explicitly go and fetch the state
    private fun getCurrentState(): State = authenticationRepository.user.toState()

}

// the different states the login screen can be in
sealed class State {

    object Anonymous : State()

    object Loading : State()

    object LoggedInUser : State()

    object Failure : State()
}

/**
 * Simple method to convert a user entity into a meaningful state
 */
private fun UserEntity.toState(): State = when (this) {
    UserEntity.UnauthenticatedUser -> State.Anonymous

    is UserEntity.AuthenticatedUser -> when (this.authenticationProvider) {
        AuthenticationProvider.Google -> State.LoggedInUser
        AuthenticationProvider.Anonymous -> State.LoggedInUser
    }
}
