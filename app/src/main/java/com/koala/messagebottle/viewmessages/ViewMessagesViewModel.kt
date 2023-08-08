package com.koala.messagebottle.viewmessages

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

private const val TAG = "ViewMessagesViewModel"

@HiltViewModel
class ViewMessagesViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _state = MutableLiveData<MessagesState>(MessagesState.Loading)
    val state: LiveData<MessagesState> = _state

    fun initialise() {

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "There was a problem fetching all messages")
            _state.value = MessagesState.Failure
        }

        viewModelScope.launch(exceptionHandler) {
            _state.value = MessagesState.Loading

            val messages = messageRepository.getMessages()

            _state.value = MessagesState.MessagesReceived(messages)
        }
    }

    fun purgeMessages() = viewModelScope.launch {
        Timber.d("TODO: purge all the messages")
    }
}

sealed class MessagesState {

    object Loading : MessagesState()

    data class MessagesReceived(val messageEntities: List<MessageEntity>) : MessagesState()

    object Failure : MessagesState()

}
