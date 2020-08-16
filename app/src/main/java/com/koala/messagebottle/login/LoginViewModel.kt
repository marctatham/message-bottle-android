package com.koala.messagebottle.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.data.authentication.AuthenticationRepository
import com.koala.messagebottle.login.data.GetCreateUserDataModel
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

    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> = _isDataAvailable

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun initiateLoginWithGoogle() {
        _dataLoading.value = true

        googleSignInProvider.initiateSignIn(object : ThirdPartyLoginProvider.Callback {
            override fun onThirdPartyLoginComplete(thirdPartyLoginCredential: ThirdPartyLoginCredential) {

                // google sign in is complete
                // control is back in our side, we want to
                // display spinner, make request
                // upon successful completion
                viewModelScope.launch {
                    authenticationRepository.firebaseAuthWithGoogle(thirdPartyLoginCredential.code)
                }

                _dataLoading.value = false
            }

            override fun onThirdPartyLoginCancelled() {
                _dataLoading.value = false
            }
        })
    }

    // complete sign in via our backend
    // this should probably go through our backend
    private fun start(thirdPartyLoginCredential: ThirdPartyLoginCredential) {

        if (_isDataAvailable.value == true || _dataLoading.value == true) {
            return
        }

        // Show loading indicator
        _dataLoading.value = true
        viewModelScope.launch {
            val getCreateUserDataModel = GetCreateUserDataModel(thirdPartyLoginCredential.code)
            //val result = userService.getCreateUser(getCreateUserDataModel)
            //Log.w(TAG, "Successful retrieval of the user ${result.jwtToken}")

            _isDataAvailable.value = true
            _dataLoading.value = false
        }

    }


}
