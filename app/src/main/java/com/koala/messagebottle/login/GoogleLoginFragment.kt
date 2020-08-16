package com.koala.messagebottle.login

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.koala.messagebottle.R

private const val TAG = "GoogleLoginFragment"

private const val REQUEST_CODE_GOOGLE = 1000

private const val ARG_EXTRA_IDENTIFIER = "identifier"
private const val ARG_EXTRA_RESULT_RECEIVER = "resultReceiver"

private const val BUNDLE_EXTRA_IS_PROCESS_RUNNING = "isProcessRunning"

class GoogleLoginFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var identifier: String
    private lateinit var resultReceiver: ResultReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        identifier = requireArguments().getString(ARG_EXTRA_IDENTIFIER)!!
        resultReceiver = requireArguments().getParcelable(ARG_EXTRA_RESULT_RECEIVER)!!

        if (!isGoogleLoginRunning(savedInstanceState)) {
            startLoginProcess()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_GOOGLE) {
            handleGoogleLoginResult(data)
        } else {
            Log.w(TAG, "unhandled request code: [$requestCode], resultCode: [$resultCode]")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(BUNDLE_EXTRA_IS_PROCESS_RUNNING, true)
    }

    private fun startLoginProcess() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        firebaseAuth = Firebase.auth
        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE)
    }

    private fun handleGoogleLoginResult(data: Intent?) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        val activity = requireActivity()
        task.addOnSuccessListener(activity) { googleSignInAccount: GoogleSignInAccount ->
            val thirdPartyLoginCredential = ThirdPartyLoginCredential(googleSignInAccount.idToken!!)
            notifyLoginComplete(thirdPartyLoginCredential)
        }.addOnFailureListener(activity) {
            notifyLoginCancelled()
        }.addOnCanceledListener(activity) {
            notifyLoginCancelled()
        }
    }

    private fun notifyLoginComplete(thirdPartyLoginCredential: ThirdPartyLoginCredential) =
        resultReceiver.send(RESULT_OK,
            Bundle().apply {
                putParcelable(RESULT_EXTRA_THIRD_PARTY_CREDENTIAL, thirdPartyLoginCredential)
                putString(RESULT_EXTRA_IDENTIFIER, identifier)
            })

    private fun notifyLoginCancelled() =
        resultReceiver.send(
            RESULT_CANCELED,
            Bundle().apply {
                putString(RESULT_EXTRA_IDENTIFIER, identifier)
            }
        )

    private fun isGoogleLoginRunning(savedInstanceState: Bundle?): Boolean =
        savedInstanceState?.getBoolean(BUNDLE_EXTRA_IS_PROCESS_RUNNING, false)
            ?: false

    companion object {

        const val RESULT_EXTRA_IDENTIFIER = "identifier"
        const val RESULT_EXTRA_THIRD_PARTY_CREDENTIAL = "third_party_credential"

        fun newInstance(identifier: String, resultReceiver: ResultReceiver): GoogleLoginFragment =
            GoogleLoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_EXTRA_IDENTIFIER, identifier)
                    putParcelable(ARG_EXTRA_RESULT_RECEIVER, resultReceiver)
                }
            }
    }
}
