package com.koala.messagebottle.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.authentication.data.AuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.AuthenticationProvider
import com.koala.messagebottle.common.authentication.domain.UserEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel for the Details screen.
 */
class LoginViewModel @Inject constructor(
    private val googleLoginProvider: ThirdPartyLoginProvider,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(getCurrentState())
    val state: LiveData<State> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception, "There was a problem signing into your account")
        _state.value = State.Failure
    }

    fun initiateLoginWithGoogle() = viewModelScope.launch(exceptionHandler) {
        val thirdPartyLoginCredential = googleLoginProvider.initiateSignIn()
        _state.value = State.Loading
        _state.value = authenticationRepository
            .firebaseAuthWithGoogle(thirdPartyLoginCredential.code)
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
    UserEntity.Anonymous -> State.Anonymous

    is UserEntity.LoggedInUser -> when (this.authenticationProvider) {
        AuthenticationProvider.Google -> State.LoggedInUser
    }
}
