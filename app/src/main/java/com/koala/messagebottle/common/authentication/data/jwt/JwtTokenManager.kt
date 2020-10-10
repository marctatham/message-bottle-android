package com.koala.messagebottle.common.authentication.data.jwt

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtTokenManager @Inject constructor() : IJwtTokenPersister, IJwtTokenProvider {

    lateinit var jwtToken: JwtToken

    override fun store(jwtToken: JwtToken) {
        this.jwtToken = jwtToken
    }

    override fun get(): JwtToken = jwtToken
}