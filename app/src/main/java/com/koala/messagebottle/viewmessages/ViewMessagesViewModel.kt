package com.koala.messagebottle.viewmessages

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.data.MessageRepository
import com.koala.messagebottle.common.messages.domain.MessageEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ViewMessagesViewModel"

class ViewMessagesViewModel @Inject constructor(
    private val messageRepository: MessageRepository

) : ViewModel() {

    private val _state = MutableLiveData<MessagesState>(MessagesState.Loading)
    val state: LiveData<MessagesState> = _state

    fun initialise() {
        viewModelScope.launch {
            _state.value = MessagesState.Loading

            val messages = messageRepository.getMessages()

            _state.value = MessagesState.MessagesReceived(messages)
        }
    }

    fun purgeMessages() = viewModelScope.launch {
        Log.d(TAG, "TODO: purge all the messages")
    }
}

sealed class MessagesState {

    object Loading : MessagesState()

    data class MessagesReceived(val messageEntities: List<MessageEntity>) : MessagesState()

}
