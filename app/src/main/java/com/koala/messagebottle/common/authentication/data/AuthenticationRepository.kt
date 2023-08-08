package com.koala.messagebottle.common.authentication.data

import com.koala.messagebottle.common.authentication.data.firebase.FirebaseAuthenticationResult
import com.koala.messagebottle.common.authentication.data.firebase.FirebaseAuthenticator
import com.koala.messagebottle.common.authentication.data.jwt.IJwtTokenPersister
import com.koala.messagebottle.common.authentication.data.jwt.JwtToken
import com.koala.messagebottle.common.authentication.domain.AuthenticationProvider
import com.koala.messagebottle.common.authentication.domain.UserEntity
import com.koala.messagebottle.common.threading.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

// Serves as a single source of truth to indicate whether the
// user is currently signed into the application
@Singleton
class AuthenticationRepository @Inject constructor(
    private val firebaseAuthenticator: FirebaseAuthenticator,
    private val jwtPersister: IJwtTokenPersister,
    @DispatcherIO private val dispatcherNetwork: CoroutineDispatcher
) {

    // TODO: let's write this to somewhere safe
    // shared prefs is probably OK for now until we've got a better usecase to introduce DB support
    // for now we'll just store this in memory since it's useful for testing auth functionality 🤷‍
    var user: UserEntity = UserEntity.Anonymous

    suspend fun firebaseAuthWithGoogle(idToken: String): UserEntity {
        val userEntity = withContext(dispatcherNetwork) {
            Timber.d("authenticating with Firebase using Google IDToken")
            val firebaseAuthResult = firebaseAuthenticator.authenticateWithGoogle(idToken)

            Timber.d("authenticating with our backend using firebase token")
//            val getCreateUserDataModel = GetCreateUserDataModel(firebaseAuthResult.token)
//            val userDataModel = userService.getCreateUser(getCreateUserDataModel)
            //mapper.map(userDataModel)
            UserEntity.LoggedInUser(AuthenticationProvider.Google, firebaseAuthResult.token)
        }

        // persist jwt token
        val jwtToken = JwtToken(userEntity.jwtToken)
        jwtPersister.store(jwtToken)

        // temporarily persist this to memory until we've got persistence mechanism in place
        user = userEntity
        return user
    }

    suspend fun signInAnonymously(): UserEntity {
        val userEntity = withContext(dispatcherNetwork) {
            Timber.d("authenticating with Firebase anonymously")
            val firebaseAuthResult: FirebaseAuthenticationResult = firebaseAuthenticator.authenticateAnonymously()

            Timber.d("User has now been authenticated")
            UserEntity.LoggedInUser(AuthenticationProvider.Anonymous, firebaseAuthResult.token)
        }

        // TODO: revisit persistence
        val jwtToken = JwtToken(userEntity.jwtToken)
        jwtPersister.store(jwtToken)

        // temporarily persist this to memory until we've got persistence mechanism in place
        user = userEntity
        return user
    }

    suspend fun signOut(): UserEntity {
        when (user) {
            UserEntity.Anonymous -> Timber.d("no sign out required for an anonymous user")

            is UserEntity.LoggedInUser -> when ((user as UserEntity.LoggedInUser).authenticationProvider) {
                AuthenticationProvider.Google -> {
                    // reset the user into anonymous mode
                    firebaseAuthenticator.signOut()
                    user = UserEntity.Anonymous
                }

                AuthenticationProvider.Anonymous -> TODO() // action anonymous signout
            }
        }

        jwtPersister.clear()

        return user
    }

}