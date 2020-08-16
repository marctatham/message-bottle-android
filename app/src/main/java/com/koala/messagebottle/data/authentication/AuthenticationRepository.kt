package com.koala.messagebottle.data.authentication

import android.util.Log
import com.koala.messagebottle.data.authentication.firebase.FirebaseAuthenticator
import com.koala.messagebottle.login.data.GetCreateUserDataModel
import com.koala.messagebottle.login.data.GetCreateUserResponseDataModel
import com.koala.messagebottle.login.data.UserService
import javax.inject.Inject

private const val TAG = "AuthenticationRepository"

// Serves as a single source of truth to indicate whether the
// user is currently signed into the application
class AuthenticationRepository @Inject constructor(
    private val firebaseAuthenticator: FirebaseAuthenticator,
    private val userService: UserService
) {

    // TODO: let's write this to somewhere safe
    // shared prefs is probably OK for now until we've got a better usecase to introduce DB support
    // for now we'll just store this in memory since it's useful for testing auth functionality 🤷‍
    private var user: GetCreateUserResponseDataModel? = null

    suspend fun isUserSignedIn(): Boolean {
        return false
    }

    // TO CONSIDER:
    // should we define our very own model for the repository?
    //  - it's probably OK that this is the DOMAIN layer defined entity
    suspend fun firebaseAuthWithGoogle(idToken: String) {
        Log.v(TAG, "authenticating with Firebase using Google IDToken")
        val firebaseAuthResult = firebaseAuthenticator.authenticateViaGoogle(idToken)

        Log.v(TAG, "authenticating with our backend using firebase token")
        val getCreateUserDataModel = GetCreateUserDataModel(firebaseAuthResult.token)
        val getCreateUserResponse = userService.getCreateUser(getCreateUserDataModel)

        // temporarily persist this to memory until we've got persistence mechanism in place
        user = getCreateUserResponse
    }

}