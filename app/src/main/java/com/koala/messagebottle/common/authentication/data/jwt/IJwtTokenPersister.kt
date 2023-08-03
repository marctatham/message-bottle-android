package com.koala.messagebottle.common.authentication.data.jwt

interface IJwtTokenPersister {

    suspend fun store(jwtToken: JwtToken)

    suspend fun clear()
}