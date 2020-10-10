package com.koala.messagebottle.common.authentication.data.jwt

interface IJwtTokenProvider {

    fun get(): JwtToken
}

