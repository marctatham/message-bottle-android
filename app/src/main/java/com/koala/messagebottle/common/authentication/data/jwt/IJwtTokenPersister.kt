package com.koala.messagebottle.common.authentication.data.jwt

interface IJwtTokenPersister {

    fun store(jwtToken: JwtToken)
}