package com.bottlecast.app.common.authentication.domain

import kotlinx.coroutines.flow.StateFlow

interface IAuthenticationRepository {

    val user: StateFlow<UserEntity>

    suspend fun firebaseAuthWithGoogle(idToken: String)

    suspend fun signInAnonymously()

    suspend fun signOut()
}