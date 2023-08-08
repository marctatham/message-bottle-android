package com.koala.messagebottle.common.authentication.data

import com.koala.messagebottle.common.authentication.data.firebase.FirebaseAuthenticationResult
import com.koala.messagebottle.common.authentication.data.firebase.FirebaseAuthenticator
import com.koala.messagebottle.common.authentication.data.jwt.IJwtTokenPersister
import com.koala.messagebottle.common.authentication.data.jwt.JwtToken
import com.koala.messagebottle.common.authentication.domain.AuthenticationProvider
import com.koala.messagebottle.common.authentication.domain.IAuthenticationRepository
import com.koala.messagebottle.common.authentication.domain.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

// Serves as a single source of truth to indicate whether the
// user is currently signed into the application
class AuthenticationRepository constructor(
    private val firebaseAuthenticator: FirebaseAuthenticator,
    private val jwtPersister: IJwtTokenPersister,
    private val dispatcherNetwork: CoroutineDispatcher
) : IAuthenticationRepository {

    // TODO: let's write this to somewhere safe
    // shared prefs is probably OK for now until we've got a better usecase to introduce DB support
    // for now we'll just store this in memory since it's useful for testing auth functionality 🤷‍
    private var _user: UserEntity = UserEntity.UnauthenticatedUser
    override val user: UserEntity get() = _user

    override suspend fun firebaseAuthWithGoogle(idToken: String): UserEntity {
        val userEntity = withContext(dispatcherNetwork) {
            Timber.d("authenticating with Firebase using Google IDToken")
            val firebaseAuthResult = firebaseAuthenticator.authenticateWithGoogle(idToken)

            Timber.d("authenticating with our backend using firebase token")
//            val getCreateUserDataModel = GetCreateUserDataModel(firebaseAuthResult.token)
//            val userDataModel = userService.getCreateUser(getCreateUserDataModel)
            //mapper.map(userDataModel)
            UserEntity.AuthenticatedUser(AuthenticationProvider.Google, firebaseAuthResult.token)
        }

        // persist jwt token
        val jwtToken = JwtToken(userEntity.jwtToken)
        jwtPersister.store(jwtToken)

        // temporarily persist this to memory until we've got persistence mechanism in place
        _user = userEntity
        return user
    }

    override suspend fun signInAnonymously(): UserEntity {
        val userEntity = withContext(dispatcherNetwork) {
            Timber.d("authenticating with Firebase anonymously")
            val firebaseAuthResult: FirebaseAuthenticationResult =
                firebaseAuthenticator.authenticateAnonymously()

            Timber.d("User has now been authenticated")
            UserEntity.AuthenticatedUser(AuthenticationProvider.Anonymous, firebaseAuthResult.token)
        }

        // TODO: revisit persistence
        val jwtToken = JwtToken(userEntity.jwtToken)
        jwtPersister.store(jwtToken)

        // temporarily persist this to memory until we've got persistence mechanism in place
        _user = userEntity
        return user
    }

    override suspend fun signOut(): UserEntity {
        // reset the user into anonymous mode
        when (_user) {
            UserEntity.UnauthenticatedUser -> Timber.d("no sign out required for an anonymous user")

            is UserEntity.AuthenticatedUser -> {
                Timber.i("Signing user out...")
                firebaseAuthenticator.signOut()
                _user = UserEntity.UnauthenticatedUser
            }
        }

        jwtPersister.clear()

        Timber.i("User has been signed out")

        return user
    }

}