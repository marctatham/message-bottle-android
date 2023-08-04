package com.koala.messagebottle.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.koala.messagebottle.R


private const val REQUEST_CODE_LOGIN = 100

class HomeFragment : Fragment() {

}

//class HomeFragment : Fragment() {
//
//    companion object {
//        fun newInstance() = HomeFragment()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
//
//        val btnSignIn = rootView.findViewById<Button>(R.id.btnSignIn)
//        val btnGetMessage = rootView.findViewById<Button>(R.id.btnGetMessage)
//        val btnPostMessage = rootView.findViewById<Button>(R.id.btnPostMessage)
//        val btnViewMessages = rootView.findViewById<Button>(R.id.btnViewMessages)
//
//        btnSignIn.setOnClickListener {
//            val intent = Intent(requireActivity(), LoginActivity::class.java)
//            startActivityForResult(intent, REQUEST_CODE_LOGIN)
//        }
//
//        btnGetMessage.setOnClickListener {
//            (requireActivity() as HomeActivity).showGetMessage()
//        }
//
//        btnPostMessage.setOnClickListener {
//            (requireActivity() as HomeActivity).showPostMessage()
//        }
//
//        btnViewMessages.setOnClickListener {
//            (requireActivity() as HomeActivity).showViewMessages()
//        }
//
//        return rootView
//    }
//
//}
