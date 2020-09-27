package com.koala.messagebottle.getmessage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.koala.messagebottle.R
import com.koala.messagebottle.home.HomeActivity
import javax.inject.Inject

class GetMessageFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var txtMessage: TextView
    private lateinit var btnGetAnotherMessage: Button

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<GetMessageViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as HomeActivity).homeComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getNewMessage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.get_message_fragment, container, false)

        txtMessage = rootView.findViewById(R.id.txtMessage)
        progressBar = rootView.findViewById(R.id.progressBar)
        btnGetAnotherMessage = rootView.findViewById(R.id.btnGetMessage)
        btnGetAnotherMessage.setOnClickListener {
            viewModel.getNewMessage()
        }

        viewModel.state.observe(viewLifecycleOwner) { state: MessageState ->
            when (state) {
                MessageState.Loading -> showProgressBar()

                is MessageState.MessageReceived -> {
                    val messageEntity = state.messageEntity
                    hideProgressBar()
                    txtMessage.text = messageEntity.message
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
        fun newInstance() = GetMessageFragment()
    }
}
