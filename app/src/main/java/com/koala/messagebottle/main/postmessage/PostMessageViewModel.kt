package com.koala.messagebottle.main.postmessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.domain.IMessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import com.koala.messagebottle.common.messages.domain.PostMessageResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostMessageViewModel @Inject constructor(
    private val messageRepository: IMessageRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MessageState> = MutableStateFlow(MessageState.Idle)
    val state: StateFlow<MessageState> = _state.asStateFlow()

    fun postMessage(message: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "There was a problem posting your message")
            _state.value = MessageState.Failure
        }

        viewModelScope.launch(exceptionHandler) {
            _state.value = MessageState.Loading
            val messageEntity = MessageEntity(message)
            when (messageRepository.postMessage(messageEntity)) {
                PostMessageResult.Success -> _state.value = MessageState.MessagePosted(messageEntity)
                PostMessageResult.Unauthenticated -> _state.value = MessageState.NotAuthenticated
            }
        }
    }
}

sealed class MessageState {

    object Idle : MessageState()

    object Loading : MessageState()

    data class MessagePosted(val messageEntity: MessageEntity) : MessageState()

    object Failure : MessageState()

    object NotAuthenticated : MessageState()

}
