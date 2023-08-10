package com.koala.messagebottle.main.getmessage

import androidx.fragment.app.Fragment


import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.snackbar.Snackbar
import com.koala.messagebottle.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetMessageFragment : Fragment() {

    private lateinit var containerView: View
    private lateinit var animView: LottieAnimationView
    private lateinit var progressBar: ProgressBar
    private lateinit var txtMessage: TextView
    private lateinit var btnGetAnotherMessage: Button

    private val viewModel: GetMessageViewModel by hiltNavGraphViewModels(R.id.app_nav)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state: MessageState ->
                    when (state) {
                        MessageState.Loading -> showLoadingState()

                        is MessageState.MessageReceived -> {
                            val messageEntity = state.messageEntity
                            hideLoadingState()
                            beginDisplayingMessage(messageEntity.message)
                        }

                        MessageState.Failure -> {
                            hideLoadingState()
                            notifyFailure()
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
        val rootView = inflater.inflate(R.layout.get_message_fragment, container, false)

        containerView = rootView.findViewById(R.id.container)
        animView = rootView.findViewById(R.id.animBottle)
        txtMessage = rootView.findViewById(R.id.txtMessage)
        progressBar = rootView.findViewById(R.id.progressBar)
        btnGetAnotherMessage = rootView.findViewById(R.id.btnGetMessage)

        btnGetAnotherMessage.setOnClickListener { viewModel.getNewMessage() }
        return rootView
    }

    private fun beginDisplayingMessage(message: String) {
        val animationListener = object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {
                txtMessage.visibility = View.INVISIBLE
                txtMessage.animate().alpha(0.0f)
            }

            override fun onAnimationEnd(animation: Animator) {
                txtMessage.visibility = View.VISIBLE
                txtMessage.animate().alpha(1.0f)
                animView.removeAllAnimatorListeners()
            }

            override fun onAnimationCancel(animation: Animator) {
                animView.removeAllAnimatorListeners()
            }

            override fun onAnimationRepeat(animation: Animator) {}
        }

        animView.addAnimatorListener(animationListener)
        animView.playAnimation()
        txtMessage.text = message

    }

    private fun showLoadingState() {
        progressBar.visibility = View.VISIBLE
        btnGetAnotherMessage.isEnabled = false
    }

    private fun hideLoadingState() {
        progressBar.visibility = View.INVISIBLE
        btnGetAnotherMessage.isEnabled = true
    }

    private fun notifyFailure() = Snackbar
        .make(containerView, R.string.snack_get_message_failed, Snackbar.LENGTH_SHORT)
        .show()


    companion object {
        fun newInstance() = GetMessageFragment()
    }
}