package com.koala.messagebottle.getmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.data.MessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository

) : ViewModel() {

    private val _state = MutableLiveData<MessageState>(MessageState.Loading)
    val state: LiveData<MessageState> = _state

    fun getNewMessage() = viewModelScope.launch {
        _state.value = MessageState.Loading

        val messageEntity = messageRepository.getMessage()
        _state.value = MessageState.MessageReceived(messageEntity)
    }
}

sealed class MessageState {

    object Loading : MessageState()

    data class MessageReceived(val messageEntity: MessageEntity) : MessageState()

}
