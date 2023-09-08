package com.koala.messagebottle.main.postmessage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostMessageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                PostViewScreen()
            }
        }
    }

    // TODO: this functionality should be handled by the new compose view
//    private fun displayPostMessageFailed() = Snackbar
//        .make(containerView, R.string.snack_post_message_failed, Snackbar.LENGTH_SHORT)
//        .show()
//
//    private fun displayLoginToPost() = Snackbar
//        .make(containerView, R.string.snack_post_message_requires_auth, Snackbar.LENGTH_SHORT)
//        .show()
}
