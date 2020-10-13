package com.koala.messagebottle.common.authentication.data.jwt

import com.koala.messagebottle.common.threading.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtTokenManager @Inject constructor(
    private val jwtSharedPrefs: JwtSharedPrefs,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : IJwtTokenPersister, IJwtTokenProvider {

    private var jwtToken: JwtToken? = null

    override suspend fun store(jwtToken: JwtToken) {
        jwtSharedPrefs.store(jwtToken)
    }

    override suspend fun clear() {
        jwtSharedPrefs.clear()
        jwtToken = null
    }

    override suspend fun get(): JwtToken? {
        if (jwtToken == null) {
            Timber.v("No cached jwt token")
            withContext(dispatcherIO) {
                jwtSharedPrefs.get()?.let {
                    jwtToken = it
                    Timber.v("Jwt Token has been retrieved & cached")
                }
            }
        }

        return jwtToken
    }
}