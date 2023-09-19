package com.koala.messagebottle.app.postmessage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koala.messagebottle.common.messages.domain.MessageEntity
import com.koala.messagebottle.app.postmessage.domain.PostMessageResult
import com.koala.messagebottle.app.postmessage.domain.PostMessageUseCase
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

    private val _state: MutableStateFlow<PostMessageUiState> =
        MutableStateFlow(PostMessageUiState.Idle)
    val state: StateFlow<PostMessageUiState> = _state.asStateFlow()

    fun postMessage(messageToPost: String) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Timber.e(exception, "There was a problem posting your message")
            _state.value = PostMessageUiState.Failure(FailureReason.Unknown)
        }

        viewModelScope.launch(exceptionHandler) {
            _state.value = PostMessageUiState.Loading

            when (val res: PostMessageResult = useCase.postMessage(messageToPost)) {
                is PostMessageResult.Success -> _state.value =
                    PostMessageUiState.Success(res.message)

                PostMessageResult.Unauthenticated -> _state.value =
                    PostMessageUiState.Failure(FailureReason.NotAuthenticated)

                PostMessageResult.InsufficientLength -> _state.value =
                    PostMessageUiState.Failure(FailureReason.InsufficientLength)
            }
        }
    }
}

sealed class FailureReason {

    data object Unknown : FailureReason()

    data object NotAuthenticated : FailureReason()

    data object InsufficientLength : FailureReason()
}

sealed class PostMessageUiState {

    data object Idle : PostMessageUiState()

    data object Loading : PostMessageUiState()

    data class Failure(val reason: FailureReason) : PostMessageUiState()

    data class Success(val messageEntity: MessageEntity) : PostMessageUiState()
}
