package com.koala.messagebottle.postmessage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.snackbar.Snackbar
import com.koala.messagebottle.R
import com.koala.messagebottle.home.HomeActivity
import javax.inject.Inject

class PostMessageFragment : Fragment() {

    private lateinit var containerView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var edtMessage: EditText
    private lateinit var btnPostMessage: Button

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<PostMessageViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as HomeActivity).homeComponent.inject(this)
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

        viewModel.state.observe(viewLifecycleOwner) { state: MessageState ->
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

                    Snackbar.make(containerView, notification, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        return rootView
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

    companion object {
        fun newInstance() = PostMessageFragment()
    }
}
