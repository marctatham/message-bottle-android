package com.koala.messagebottle.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.data.authentication.AuthenticationRepository
import com.koala.messagebottle.common.domain.AuthenticationProvider
import com.koala.messagebottle.common.domain.UserEntity
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

    private val _state = MutableLiveData<State>(
        authenticationRepository.user.toState()
    )
    val state: LiveData<State> = _state

    fun initiateLoginWithGoogle() {
        _state.value = State.Loading

        googleSignInProvider.initiateSignIn(object : ThirdPartyLoginProvider.Callback {
            override fun onThirdPartyLoginComplete(thirdPartyLoginCredential: ThirdPartyLoginCredential) {

                // google sign in is complete
                // control is back in our side, we want to
                // display spinner, make request
                // upon successful completion
                viewModelScope.launch {
                    authenticationRepository.firebaseAuthWithGoogle(thirdPartyLoginCredential.code)

                    _state.value = authenticationRepository.user.toState()
                }
            }

            override fun onThirdPartyLoginCancelled() {

                _state.value = authenticationRepository.user.toState()
            }
        })
    }

    fun initiateSignOut() = viewModelScope.launch {
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
