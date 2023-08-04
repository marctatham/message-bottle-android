package com.koala.messagebottle.viewmessages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.koala.messagebottle.R

class ViewMessagesFragment : Fragment() {

}

//class ViewMessagesFragment : Fragment() {
//
//    private lateinit var containerView: View
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var progressBar: ProgressBar
//    private lateinit var btnPurge: Button
//
//    private lateinit var messagesAdapter: MessagesAdapter
//
//    private val viewModel: ViewMessagesViewModel by hiltNavGraphViewModels(R.id.nav_host_fragment)
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val rootView = inflater.inflate(R.layout.view_messages_fragment, container, false)
//        containerView = rootView.findViewById(R.id.container)
//        recyclerView = rootView.findViewById(R.id.recyclerView)
//        progressBar = rootView.findViewById(R.id.progressBar)
//        btnPurge = rootView.findViewById(R.id.btnPurge)
//        btnPurge.setOnClickListener {
//            viewModel.purgeMessages()
//        }
//
//        // configure the ol' recycler view & adapter
//        messagesAdapter = MessagesAdapter(LayoutInflater.from(requireContext()))
//        recyclerView.adapter = messagesAdapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        viewModel.initialise()
//
//        viewModel.state.observe(viewLifecycleOwner) { state: MessagesState ->
//            when (state) {
//                MessagesState.Loading -> showProgressBar()
//
//                is MessagesState.MessagesReceived -> {
//                    hideProgressBar()
//                    messagesAdapter.messages = state.messageEntities
//                    messagesAdapter.notifyDataSetChanged()
//                }
//
//                MessagesState.Failure -> {
//                    hideProgressBar()
//                    displayGetMessagesFailed()
//                }
//            }
//        }
//
//        return rootView
//    }
//
//    private fun showProgressBar() {
//        progressBar.visibility = View.VISIBLE
//    }
//
//    private fun hideProgressBar() {
//        progressBar.visibility = View.INVISIBLE
//    }
//
//    private fun displayGetMessagesFailed() = Snackbar
//        .make(containerView, R.string.get_messages_failed, Snackbar.LENGTH_SHORT)
//        .show()
//
//    companion object {
//        fun newInstance() = ViewMessagesFragment()
//    }
//}
