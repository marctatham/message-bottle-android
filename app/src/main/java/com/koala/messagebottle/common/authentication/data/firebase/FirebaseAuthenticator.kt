package com.koala.messagebottle.common.authentication.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FirebaseAuthenticator @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun authenticateViaGoogle(idToken: String): FirebaseAuthenticationResult {
        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        val taskSignInWithCredential = firebaseAuth.signInWithCredential(authCredential)
        val authResult: AuthResult = taskSignInWithCredential.await()

        Timber.i("retrieving FirebaseUser's IdToken")
        val firebaseUser: FirebaseUser = authResult.user!!
        val taskGetIdToken: Task<GetTokenResult> = firebaseUser.getIdToken(false)
        val tokenResult: GetTokenResult = taskGetIdToken.await()

        Timber.w("Received token result...")
        Timber.w("Token - [${tokenResult.token}]")
        Timber.w("Signin Provider - [${tokenResult.signInProvider}]")
        tokenResult.claims.forEach {
            Timber.w("[${it.key}] - [${it.value}]")
        }

        val token = tokenResult.token!!
        return FirebaseAuthenticationResult(token)
    }

    // TODO: forget stored JWT token
    // at some point we can extend our backend to invalidate the JWT token that was issued to the client
    // for now simply "forgetting" the JWT token we've been issued (which isn't currently being stored)
    // and officially signing out via firebase should be sufficient
    suspend fun signOut() = firebaseAuth.signOut()

}
