package com.koala.messagebottle.login

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import androidx.fragment.app.FragmentManager
import javax.inject.Inject

class GoogleLoginProvider @Inject constructor(
    private val fragmentManager: FragmentManager
) : ThirdPartyLoginProvider {

    private val callbacksMap: MutableMap<String, ThirdPartyLoginProvider.Callback> = mutableMapOf()
    private val resultReceiver: ResultReceiver = object : ResultReceiver(
        Handler(Looper.getMainLooper())
    ) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            super.onReceiveResult(resultCode, resultData)
            processResult(resultCode, resultData)
        }
    }

    override fun initiateSignIn(callback: ThirdPartyLoginProvider.Callback) {
        val identifier = System.currentTimeMillis().toString()
        val cardScannerFragment = GoogleLoginFragment.newInstance(identifier, resultReceiver)
        callbacksMap.put(identifier, callback)
        addFragment(identifier, cardScannerFragment)
    }

    private fun processResult(resultCode: Int, resultData: Bundle) {
        val fragmentTag = resultData.getString(GoogleLoginFragment.RESULT_EXTRA_IDENTIFIER)!!
        val thirdPartyLoginCallbackList = callbacksMap.remove(fragmentTag)


        // notify the callback
        thirdPartyLoginCallbackList?.let { callback ->
            if (resultCode == Activity.RESULT_OK) {
                val thirdPartyLoginCredential =
                    resultData.getParcelable<ThirdPartyLoginCredential>(GoogleLoginFragment.RESULT_EXTRA_THIRD_PARTY_CREDENTIAL)!!
                callback.onThirdPartyLoginComplete(thirdPartyLoginCredential)
            } else {
                callback.onThirdPartyLoginCancelled()
            }
        }

        removeFragment(fragmentTag)
    }

    private fun addFragment(fragmentTag: String, cardScannerFragment: GoogleLoginFragment) {
        Handler().post {
            fragmentManager.beginTransaction().apply {
                add(cardScannerFragment, fragmentTag)
                commit()
            }
        }
    }

    private fun removeFragment(fragmentTag: String) {
        if (canRemoveFragment(fragmentTag)) {
            fragmentManager.beginTransaction().apply {
                fragmentManager.findFragmentByTag(fragmentTag)?.let { remove(it) }
                commit()
            }
        }
    }

    private fun canRemoveFragment(fragmentTag: String): Boolean =
        fragmentManager.findFragmentByTag(fragmentTag)?.let {
            isActivityActive(it.activity)
        } ?: false

    private fun isActivityActive(activity: Activity?): Boolean = when (activity) {
        null -> false
        else -> !activity.isFinishing
    }

}
