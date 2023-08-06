package com.koala.messagebottle.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.koala.messagebottle.R
import dagger.hilt.android.AndroidEntryPoint


private const val REQUEST_CODE_LOGIN = 100


@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)

        val btnSignIn = rootView.findViewById<Button>(R.id.btnSignIn)
        val btnGetMessage = rootView.findViewById<Button>(R.id.btnGetMessage)
        val btnPostMessage = rootView.findViewById<Button>(R.id.btnPostMessage)
        val btnViewMessages = rootView.findViewById<Button>(R.id.btnViewMessages)

        // let's see if it's been injected oK
        viewModel.test()

        btnSignIn.setOnClickListener {
            Snackbar
                .make(rootView, "Launch Login Flow", Snackbar.LENGTH_SHORT)
                .show()

            // TODO: re-introduce
            // val intent = Intent(requireActivity(), LoginActivity::class.java)
            // startActivityForResult(intent, REQUEST_CODE_LOGIN)
        }

        btnGetMessage.setOnClickListener {
            (requireActivity() as HomeActivity).showGetMessage()
        }

        btnPostMessage.setOnClickListener {
            (requireActivity() as HomeActivity).showPostMessage()
        }

        btnViewMessages.setOnClickListener {
            (requireActivity() as HomeActivity).showViewMessages()
        }

        return rootView
    }

}
