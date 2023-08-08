package com.koala.messagebottle.login.google

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import androidx.fragment.app.FragmentManager
import com.koala.messagebottle.login.ThirdPartyLoginCredential
import com.koala.messagebottle.login.ThirdPartyLoginProvider
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GoogleLoginProvider @Inject constructor(
    private val fragmentManager: FragmentManager
) : ThirdPartyLoginProvider {

    private val callbacksMap: MutableMap<String, Continuation<ThirdPartyLoginCredential>> =
        mutableMapOf()
    private val resultReceiver: ResultReceiver = object : ResultReceiver(
        Handler(Looper.getMainLooper())
    ) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            super.onReceiveResult(resultCode, resultData)
            processResult(resultCode, resultData)
        }
    }

    override suspend fun initiateSignIn(): ThirdPartyLoginCredential =
        suspendCoroutine { continuation ->
            val identifier = System.currentTimeMillis().toString()
            val cardScannerFragment = GoogleLoginFragment.newInstance(identifier, resultReceiver)
            callbacksMap[identifier] = continuation
            addFragment(identifier, cardScannerFragment)
        }

    private fun processResult(resultCode: Int, resultData: Bundle) {
        val fragmentTag = resultData.getString(GoogleLoginFragment.RESULT_EXTRA_IDENTIFIER)!!
        callbacksMap.remove(fragmentTag)?.let { continuation ->
            if (resultCode == Activity.RESULT_OK) {
                val thirdPartyLoginCredential =
                    resultData.getParcelable<ThirdPartyLoginCredential>(GoogleLoginFragment.RESULT_EXTRA_THIRD_PARTY_CREDENTIAL)!!
                continuation.resume(thirdPartyLoginCredential)
            } else {
                continuation.resumeWithException(CancellationException())
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