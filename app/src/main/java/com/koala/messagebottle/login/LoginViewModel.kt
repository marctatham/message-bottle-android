package com.koala.messagebottle.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.data.authentication.AuthenticationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

private const val NONE = 0
private const val LOADING = 1
private const val DONE = 2


/**
 * ViewModel for the Details screen.
 */
class LoginViewModel @Inject constructor(
    private val googleSignInProvider: ThirdPartyLoginProvider,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    private val _state = MutableLiveData<Int>()

    // TODO: subscribe for changes to the viewmodel state
    val state: LiveData<Int> = _state

    fun initiateLoginWithGoogle() {
        _state.value = LOADING

        googleSignInProvider.initiateSignIn(object : ThirdPartyLoginProvider.Callback {
            override fun onThirdPartyLoginComplete(thirdPartyLoginCredential: ThirdPartyLoginCredential) {

                // google sign in is complete
                // control is back in our side, we want to
                // display spinner, make request
                // upon successful completion
                viewModelScope.launch {
                    authenticationRepository.firebaseAuthWithGoogle(thirdPartyLoginCredential.code)
                }

                _state.value = DONE
            }

            override fun onThirdPartyLoginCancelled() {
                _state.value = NONE
            }
        })
    }
}

