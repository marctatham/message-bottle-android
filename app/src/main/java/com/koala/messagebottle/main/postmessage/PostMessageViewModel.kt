package com.koala.messagebottle.main.postmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.data.MessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _state = MutableLiveData<MessageState>(MessageState.Idle)
    val state: LiveData<MessageState> = _state

    fun postMessage(message: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "There was a problem posting your message")
            _state.value = MessageState.Failure
        }

        viewModelScope.launch(exceptionHandler) {
            _state.value = MessageState.Loading

            val messageEntity = MessageEntity(message)
            messageRepository.postMessage(messageEntity)

            _state.value = MessageState.MessagePosted(messageEntity)
        }
    }
}

sealed class MessageState {

    object Idle : MessageState()

    object Loading : MessageState()

    data class MessagePosted(val messageEntity: MessageEntity) : MessageState()

    object Failure : MessageState()

}
