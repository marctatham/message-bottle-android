package com.koala.messagebottle.common.authentication.data.firebase

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseAuthenticator"

class FirebaseAuthenticator @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun authenticateViaGoogle(idToken: String): FirebaseAuthenticationResult {
        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        val taskSignInWithCredential = firebaseAuth.signInWithCredential(authCredential)
        val authResult: AuthResult = taskSignInWithCredential.await()

        Log.i(TAG, "retrieving FirebaseUser's IdToken")
        val firebaseUser: FirebaseUser = authResult.user!!
        val taskGetIdToken: Task<GetTokenResult> = firebaseUser.getIdToken(false)
        val tokenResult: GetTokenResult = taskGetIdToken.await()

        Log.w(TAG, "Received token result...")
        Log.w(TAG, "Token - [${tokenResult.token}]")
        Log.w(TAG, "Signin Provider - [${tokenResult.signInProvider}]")
        tokenResult.claims.forEach {
            Log.w(TAG, "[${it.key}] - [${it.value}]")
        }

        val token = tokenResult.token!!
        return FirebaseAuthenticationResult(token)
    }
}
