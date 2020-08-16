package com.koala.messagebottle.login

interface ThirdPartyLoginProvider {

    fun initiateSignIn(callback: Callback)

    interface Callback {

        fun onThirdPartyLoginComplete(thirdPartyLoginCredential: ThirdPartyLoginCredential)

        fun onThirdPartyLoginCancelled()
    }

}
