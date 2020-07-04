package com.koala.messagebottle.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.login.data.GetCreateUserDataModel
import com.koala.messagebottle.login.data.UserService
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginViewModel"

/**
 * ViewModel for the Details screen.
 */
class LoginViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {

    private val _isDataAvailable = MutableLiveData<Boolean>()
    val isDataAvailable: LiveData<Boolean> = _isDataAvailable

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading


    fun start(token: String) {
        if (_isDataAvailable.value == true || _dataLoading.value == true) {
            return
        }

        // Show loading indicator
        _dataLoading.value = true
        viewModelScope.launch {
            val getCreateUserDataModel = GetCreateUserDataModel(token)
            val result = userService.getCreateUser(getCreateUserDataModel)
            Log.w(TAG, "Successful retrieval of the user ${result.jwtToken}")

            _isDataAvailable.value = true
            _dataLoading.value = false
        }

    }
}
