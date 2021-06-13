package com.koala.messagebottle.viewmessages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.koala.messagebottle.R
import com.koala.messagebottle.home.HomeActivity
import javax.inject.Inject

class ViewMessagesFragment : Fragment() {

    private lateinit var containerView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnPurge: Button

    private lateinit var messagesAdapter: MessagesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ViewMessagesViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as HomeActivity).homeComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.view_messages_fragment, container, false)
        containerView = rootView.findViewById(R.id.container)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        progressBar = rootView.findViewById(R.id.progressBar)
        btnPurge = rootView.findViewById(R.id.btnPurge)
        btnPurge.setOnClickListener {
            viewModel.purgeMessages()
        }

        // configure the ol' recycler view & adapter
        messagesAdapter = MessagesAdapter(LayoutInflater.from(requireContext()))
        recyclerView.adapter = messagesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.initialise()

        viewModel.state.observe(viewLifecycleOwner) { state: MessagesState ->
            when (state) {
                MessagesState.Loading -> showProgressBar()

                is MessagesState.MessagesReceived -> {
                    hideProgressBar()
                    messagesAdapter.messages = state.messageEntities
                    messagesAdapter.notifyDataSetChanged()
                }

                MessagesState.Failure -> {
                    hideProgressBar()
                    displayGetMessagesFailed()
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

    private fun displayGetMessagesFailed() = Snackbar
        .make(containerView, R.string.get_messages_failed, Snackbar.LENGTH_SHORT)
        .show()

    companion object {
        fun newInstance() = ViewMessagesFragment()
    }
}
