package com.koala.messagebottle.postmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.data.MessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class PostMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository

) : ViewModel() {

    private val _state = MutableLiveData<MessageState>(MessageState.Idle)
    val state: LiveData<MessageState> = _state

    fun postMessage(message: String) = viewModelScope.launch {
        _state.value = MessageState.Loading

        val messageEntity = MessageEntity(message)
        messageRepository.postMessage(messageEntity)

        _state.value = MessageState.MessagePosted(messageEntity)
    }
}

sealed class MessageState {

    object Idle : MessageState()

    object Loading : MessageState()

    data class MessagePosted(val messageEntity: MessageEntity) : MessageState()

}
