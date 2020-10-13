package com.koala.messagebottle.common.authentication.data.jwt

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_JWT_TOKEN = "JWT_TOKEN"

/**
 * Manages persistence of the JWT token to shared preferences
 */
@Singleton
class JwtSharedPrefs @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : IJwtTokenPersister, IJwtTokenProvider {

    override suspend fun store(jwtToken: JwtToken) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_JWT_TOKEN, jwtToken.token)
        editor.apply()
    }

    override suspend fun get(): JwtToken? {
        if (sharedPreferences.contains(KEY_JWT_TOKEN)) {
            val token: String = sharedPreferences.getString(KEY_JWT_TOKEN, "")!!
            return JwtToken(token)
        }

        return null
    }
}