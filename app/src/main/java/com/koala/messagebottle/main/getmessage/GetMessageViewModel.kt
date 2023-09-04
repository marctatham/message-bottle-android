package com.koala.messagebottle.main.getmessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.domain.IMessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GetMessageViewModel @Inject constructor(
    private val messageRepository: IMessageRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MessageState> = MutableStateFlow(MessageState.PlayingAnimation)
    val state: StateFlow<MessageState> = _state.asStateFlow()

    init {
        getNewMessage()
    }

    fun getNewMessage() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "There was a problem retrieving the message")
            _state.value = MessageState.Failure
        }

        viewModelScope.launch(exceptionHandler) {
            _state.value = MessageState.PlayingAnimation

            val messageEntity = messageRepository.getMessage()

            _state.value = MessageState.MessageReceived(messageEntity)
        }
    }
}

sealed class MessageState {

    data object PlayingAnimation : MessageState()

    data class MessageReceived(val messageEntity: MessageEntity) : MessageState()

    data object Failure : MessageState()
}
