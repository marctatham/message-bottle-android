package com.koala.messagebottle.common.authentication.data

import com.koala.messagebottle.common.authentication.data.firebase.FirebaseAuthenticator
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
    private val userService: UserService,
    private val mapper: UserDataModelMapper,
    @DispatcherIO private val dispatcherNetwork: CoroutineDispatcher
) {

    // TODO: let's write this to somewhere safe
    // shared prefs is probably OK for now until we've got a better usecase to introduce DB support
    // for now we'll just store this in memory since it's useful for testing auth functionality 🤷‍
    var user: UserEntity = UserEntity.Anonymous

    suspend fun firebaseAuthWithGoogle(idToken: String): UserEntity {
        val userEntity = withContext(dispatcherNetwork) {
            Timber.v("authenticating with Firebase using Google IDToken")
            val firebaseAuthResult = firebaseAuthenticator.authenticateViaGoogle(idToken)

            Timber.v("authenticating with our backend using firebase token")
            val getCreateUserDataModel = GetCreateUserDataModel(firebaseAuthResult.token)
            val userDataModel = userService.getCreateUser(getCreateUserDataModel)
            mapper.map(userDataModel)
        }

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
            }
        }

        return user
    }

}