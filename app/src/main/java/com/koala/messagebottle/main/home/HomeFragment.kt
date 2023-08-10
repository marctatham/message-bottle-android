package com.koala.messagebottle.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.koala.messagebottle.R
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.main.Constants.REQUEST_CODE_LOGIN
import com.koala.messagebottle.main.MainActivity

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)

        val btnSignIn = rootView.findViewById<Button>(R.id.btnSignIn)
        val btnGetMessage = rootView.findViewById<Button>(R.id.btnGetMessage)
        val btnPostMessage = rootView.findViewById<Button>(R.id.btnPostMessage)
        val btnViewMessages = rootView.findViewById<Button>(R.id.btnViewMessages)

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
