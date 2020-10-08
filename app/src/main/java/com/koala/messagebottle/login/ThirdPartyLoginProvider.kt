package com.koala.messagebottle.login

interface ThirdPartyLoginProvider {

    suspend fun initiateSignIn(): ThirdPartyLoginCredential
}
