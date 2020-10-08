package com.koala.messagebottle.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.authentication.data.AuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.AuthenticationProvider
import com.koala.messagebottle.common.authentication.domain.UserEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

/**
 * ViewModel for the Details screen.
 */
class LoginViewModel @Inject constructor(
    private val googleSignInProvider: ThirdPartyLoginProvider,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _state = MutableLiveData(
        authenticationRepository.user.toState()
    )
    val state: LiveData<State> = _state

    fun initiateLoginWithGoogle() {
        _state.value = State.Loading

        viewModelScope.launch {
            val thirdPartyLoginCredential = googleSignInProvider.initiateSignIn()
            authenticationRepository.firebaseAuthWithGoogle(thirdPartyLoginCredential.code)

            _state.value = authenticationRepository.user.toState()
        }
    }

    fun initiateSignOut() = viewModelScope.launch {
        _state.value = State.Loading
        authenticationRepository.signOut()
        _state.value = authenticationRepository.user.toState()
    }
}

// the different states the login screen can be in
sealed class State {

    object Anonymous : State()

    object Loading : State()

    object GoogleUser : State()

}

/**
 * Simple method to convert a user entity into a meaningful state
 */
private fun UserEntity.toState(): State = when (this) {
    UserEntity.Anonymous -> State.Anonymous

    is UserEntity.LoggedInUser -> when (this.authenticationProvider) {
        AuthenticationProvider.Google -> State.GoogleUser
    }
}
