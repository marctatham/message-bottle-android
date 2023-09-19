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
    PostMessageScreen(onBackHandler, state, onPostHandler)
}

@Composable
private fun PostMessageScreen(
    onBackHandler: () -> Unit,
    uiState: PostMessageUiState,
    onPostHandler: (messageToPost: String) -> Unit,
) {
    var textState: String by rememberSaveable { mutableStateOf("") }
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
            modifier = Modifier.weight(1f),
            value = textState,
            onValueChange = { textState = it },
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

        if (uiState is PostMessageUiState.Failure) {
            BottlingFailureReason(
                failure = uiState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(id = R.string.post_message_char_count, textState.length),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        BottlingButton(
            text = R.string.btnPostMessage,
            enabled = isPostButtonEnabled(uiState),
            onTapHandler = { onPostHandler(textState) },
            isLoading = uiState is PostMessageUiState.Loading,
            modifier = Modifier.padding(16.dp),
        )
    }
}

private fun isPostButtonEnabled(uiState: PostMessageUiState): Boolean = when (uiState) {
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
