package com.koala.messagebottle.main.postmessage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.domain.MessageEntity
import com.koala.messagebottle.main.postmessage.domain.PostMessageResult
import com.koala.messagebottle.main.postmessage.domain.PostMessageUseCase
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
    private val useCase: PostMessageUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<PostMessageUiState> = MutableStateFlow(PostMessageUiState.Idle)
    val state: StateFlow<PostMessageUiState> = _state.asStateFlow()

    fun postMessage(messageToPost: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "There was a problem posting your message")
            _state.value = PostMessageUiState.Failure
        }

        viewModelScope.launch(exceptionHandler) {
            _state.value = PostMessageUiState.Loading

            when (val res: PostMessageResult = useCase.postMessage(messageToPost)) {
                is PostMessageResult.Success -> _state.value =
                    PostMessageUiState.MessagePosted(res.message)

                PostMessageResult.Unauthenticated -> _state.value = PostMessageUiState.NotAuthenticated
            }
        }
    }
}

sealed class PostMessageUiState {

    data object Idle : PostMessageUiState()

    data object Loading : PostMessageUiState()

    data class MessagePosted(val messageEntity: MessageEntity) : PostMessageUiState()

    data object Failure : PostMessageUiState()

    data object NotAuthenticated : PostMessageUiState()

}
