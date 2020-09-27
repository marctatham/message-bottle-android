package com.koala.messagebottle.getmessage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMessageViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<MessageState>(MessageState.Loading)
    val state: LiveData<MessageState> = _state

    // TODO: retrieve messages from remote host, for now lets simply simulate it
    val messages = listOf(
        "\uD83D\uDC28I love you\uD83C\uDF37\n ♥️prettygirl♥",
        "♥Holaaa♥\n\uD83D\uDC28Guapaaa\uD83C\uDF37",
        "\uD83C\uDF35\uD83E\uDDC0\uD83D\uDCF7\uD83D\uDC28\uD83C\uDFD6"
    )

    fun initialise() = viewModelScope.launch {
        // simply simulate fetching from remote server for now
        delay(1000L)

        _state.value = MessageState.MessageReceived(messages.shuffled().first())
    }
}

sealed class MessageState {

    object Loading : MessageState()

    data class MessageReceived(val message: String) : MessageState()

}
