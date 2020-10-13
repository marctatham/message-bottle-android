package com.koala.messagebottle.common.authentication.data.jwt

interface IJwtTokenProvider {

    suspend fun get(): JwtToken?
}

