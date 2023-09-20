package com.koala.messagebottle.app.postmessage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.koala.messagebottle.R
import com.koala.messagebottle.app.postmessage.domain.MINIMUM_MESSAGE_LENGTH
import com.koala.messagebottle.common.components.BottlingAppBar
import com.koala.messagebottle.common.components.BottlingButton
import com.koala.messagebottle.common.messages.domain.MessageEntity

@Composable
fun PostMessageScreen(
    onBackHandler: () -> Unit,
    viewModel: PostMessageViewModel = hiltViewModel(),
) {
    val state: PostMessageUiState by viewModel.state.collectAsStateWithLifecycle()
    val onPostHandler: (messageToPost: String) -> Unit = { viewModel.postMessage(it) }
    when (state) {
        is PostMessageUiState.Failure,
        PostMessageUiState.Idle,
        PostMessageUiState.Loading -> PostMessageScreen(onBackHandler, state, onPostHandler)

        is PostMessageUiState.Success -> PostMessageSuccessView(onCompletionHandler = onBackHandler)
    }
}

@Composable
private fun PostMessageScreen(
    onBackHandler: () -> Unit,
    uiState: PostMessageUiState,
    onPostHandler: (messageToPost: String) -> Unit,
) {
    var textState: String by rememberSaveable { mutableStateOf("") }
    val isCurrentlyInError = uiState is PostMessageUiState.Failure
    val isMessageLongEnough = textState.length >= MINIMUM_MESSAGE_LENGTH
    val isCurrentlyEditable = isCurrentlyEditable(uiState)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        BottlingAppBar(
            onBackHandler = onBackHandler,
            modifier = Modifier.fillMaxWidth(),
            title = R.string.title_post_message
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = textState,
            onValueChange = { textState = it },
            enabled = isCurrentlyEditable,
            singleLine = false,
            placeholder = {
                Text(text = stringResource(R.string.post_message_hint))
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = true
            ),
        )

        if (isCurrentlyInError) {
            BottlingFailureReason(
                failure = uiState as PostMessageUiState.Failure,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = if (isMessageLongEnough) textState.length.toString() else stringResource(
                id = R.string.post_message_char_count,
                textState.length,
                MINIMUM_MESSAGE_LENGTH
            ),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
            color = if (isMessageLongEnough) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.error
        )

        BottlingButton(
            text = R.string.btnPostMessage,
            enabled = isCurrentlyEditable && isMessageLongEnough,
            onTapHandler = { onPostHandler(textState) },
            isLoading = uiState is PostMessageUiState.Loading,
            modifier = Modifier.padding(16.dp),
        )
    }
}

private fun isCurrentlyEditable(uiState: PostMessageUiState): Boolean = when (uiState) {
    is PostMessageUiState.Failure,
    PostMessageUiState.Idle -> true

    PostMessageUiState.Loading,
    is PostMessageUiState.Success -> false
}

@Preview
@Composable
fun PostViewPreviewIdle() {
    PostMessageScreen({}, PostMessageUiState.Idle, onPostHandler = {})
}

@Preview
@Composable
fun PostViewPreviewFailure() {
    PostMessageScreen(
        {},
        PostMessageUiState.Failure(FailureReason.NotAuthenticated),
        onPostHandler = {})
}

@Preview
@Composable
fun PostViewPreviewSuccess() {
    PostMessageScreen({}, PostMessageUiState.Success(MessageEntity("", "")), onPostHandler = {})
}
