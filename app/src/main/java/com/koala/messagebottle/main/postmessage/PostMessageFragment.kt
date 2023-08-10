package com.koala.messagebottle.main.postmessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.koala.messagebottle.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PostMessageFragment : Fragment() {

    private lateinit var containerView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var edtMessage: EditText
    private lateinit var btnPostMessage: Button

    private val viewModel: PostMessageViewModel by hiltNavGraphViewModels(R.id.app_nav)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state: MessageState ->
                    when (state) {
                        MessageState.Idle -> hideProgressBar()

                        MessageState.Loading -> showProgressBar()

                        is MessageState.MessagePosted -> {
                            hideProgressBar()

                            val notification = getString(
                                R.string.snack_posted_message,
                                state.messageEntity.message
                            )

                            edtMessage.text.clear()

                            Snackbar.make(containerView, notification, Snackbar.LENGTH_SHORT).show()
                        }

                        MessageState.Failure -> {
                            hideProgressBar()
                            displayPostMessageFailed()
                        }

                        MessageState.NotAuthenticated -> {
                            hideProgressBar()
                            displayLoginToPost()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.post_message_fragment, container, false)

        containerView = rootView.findViewById(R.id.container)
        edtMessage = rootView.findViewById(R.id.edtMessage)
        progressBar = rootView.findViewById(R.id.progressBar)
        btnPostMessage = rootView.findViewById(R.id.btnPostMessage)
        btnPostMessage.setOnClickListener {
            val message = edtMessage.text.toString()
            viewModel.postMessage(message)
        }

        return rootView
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    private fun displayPostMessageFailed() = Snackbar
        .make(containerView, R.string.snack_post_message_failed, Snackbar.LENGTH_SHORT)
        .show()

    private fun displayLoginToPost() = Snackbar
        .make(containerView, R.string.snack_post_message_requires_auth, Snackbar.LENGTH_SHORT)
        .show()
}
