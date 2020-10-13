package com.koala.messagebottle.getmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.data.MessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class GetMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository

) : ViewModel() {

    private val _state = MutableLiveData<MessageState>(MessageState.Loading)
    val state: LiveData<MessageState> = _state


    fun getNewMessage() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "There was a problem retrieving the message")
            _state.value = MessageState.Failure
        }

        viewModelScope.launch(exceptionHandler) {
            _state.value = MessageState.Loading

            val messageEntity = messageRepository.getMessage()

            _state.value = MessageState.MessageReceived(messageEntity)
        }
    }
}

sealed class MessageState {

    object Loading : MessageState()

    data class MessageReceived(val messageEntity: MessageEntity) : MessageState()

    object Failure : MessageState()
}