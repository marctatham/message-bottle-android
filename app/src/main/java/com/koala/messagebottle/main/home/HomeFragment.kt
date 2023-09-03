package com.koala.messagebottle.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.koala.messagebottle.login.LoginActivity
import com.koala.messagebottle.main.Constants.REQUEST_CODE_LOGIN
import com.koala.messagebottle.main.MainActivity

class HomeFragment : Fragment() {

    private val onSignInHandler = {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_LOGIN)
    }

    private val onGetMessageHandler = { (requireActivity() as MainActivity).showGetMessage() }

    private val onPostMessageHandler = { (requireActivity() as MainActivity).showPostMessage() }

    private val onViewMessageHandler = { (requireActivity() as MainActivity).showViewMessages() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                HomeView(
                    onSignInHandler,
                    onGetMessageHandler,
                    onPostMessageHandler,
                    onViewMessageHandler
                )
            }
        }
    }

}
