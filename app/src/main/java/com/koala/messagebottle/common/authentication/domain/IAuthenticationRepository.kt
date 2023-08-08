package com.koala.messagebottle.common.authentication.domain

interface IAuthenticationRepository {

    val user: UserEntity

    suspend fun firebaseAuthWithGoogle(idToken: String): UserEntity

    suspend fun signInAnonymously(): UserEntity

    suspend fun signOut(): UserEntity
}