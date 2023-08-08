package com.koala.messagebottle.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.koala.messagebottle.R
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.main.Constants.REQUEST_CODE_LOGIN
import com.koala.messagebottle.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

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
             val intent = Intent(requireActivity(), LoginActivity::class.java)
             startActivityForResult(intent, REQUEST_CODE_LOGIN)
        }

        btnGetMessage.setOnClickListener {
            (requireActivity() as MainActivity).showGetMessage()
        }

        btnPostMessage.setOnClickListener {
            (requireActivity() as MainActivity).showPostMessage()
        }

        btnViewMessages.setOnClickListener {
            (requireActivity() as MainActivity).showViewMessages()
        }

        return rootView
    }

}
