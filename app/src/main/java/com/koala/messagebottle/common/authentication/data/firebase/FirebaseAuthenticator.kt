package com.koala.messagebottle.common.authentication.data.firebase

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.*
import com.koala.messagebottle.common.authentication.domain.AuthenticationResult
import timber.log.Timber
import javax.inject.Inject

class FirebaseAuthenticator @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun authenticateWithGoogle(idToken: String): AuthenticationResult {
        Timber.i("[authenticateWithGoogle] Authenticating into FirebaseUser's for user's Google Provided IdToken")
        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        val taskSignInWithCredential: Task<AuthResult> =
            firebaseAuth.signInWithCredential(authCredential)
        val authResult: AuthResult = Tasks.await(taskSignInWithCredential)
        val taskGetIdToken: Task<GetTokenResult> = authResult.user!!.getIdToken(false)
        val tokenResult: GetTokenResult = Tasks.await(taskGetIdToken)

        Timber.i("[authenticateWithGoogle] Received token result: Signin Provider - [${tokenResult.signInProvider}] Token - [${tokenResult.token}]")
        tokenResult.claims.forEach {
            Timber.d("[${it.key}] - [${it.value}]")
        }

        return AuthenticationResult(tokenResult.token!!)
    }

    suspend fun authenticateAnonymously(): AuthenticationResult {
        Timber.i("[authenticateAnonymously] Authenticating Anonymously")
        val taskSignInWithCredential: Task<AuthResult> = firebaseAuth.signInAnonymously()

        val authResult: AuthResult = Tasks.await(taskSignInWithCredential)
        val taskGetIdToken: Task<GetTokenResult> = authResult.user!!.getIdToken(false)
        val tokenResult: GetTokenResult = Tasks.await(taskGetIdToken)

        Timber.i("[authenticateAnonymously] Received token result: Signin Provider - [${tokenResult.signInProvider}] Token - [${tokenResult.token}]")
        tokenResult.claims.forEach {
            Timber.d("[${it.key}] - [${it.value}]")
        }

        return AuthenticationResult(tokenResult.token!!)
    }

    // TODO: forget stored JWT token
    // at some point we can extend our backend to invalidate the JWT token that was issued to the client
    // for now simply "forgetting" the JWT token we've been issued (which isn't currently being stored)
    // and officially signing out via firebase should be sufficient
    suspend fun signOut() = firebaseAuth.signOut()

}
