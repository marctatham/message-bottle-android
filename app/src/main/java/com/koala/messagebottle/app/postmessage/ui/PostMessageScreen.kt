package com.koala.messagebottle.app.postmessage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.BottlingAppBar
import com.koala.messagebottle.common.components.BottlingButton
import com.koala.messagebottle.common.messages.domain.MessageEntity
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val wrappedPostHandler: () -> Unit = {
        scope.launch { onPostHandler(textState) }
            .invokeOnCompletion {
                onBackHandler()
            }
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        BottlingAppBar(
            onBackHandler = onBackHandler,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        if (uiState is PostMessageUiState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
                strokeWidth = 4.dp
            )
        } else {
            BottlingMessageCard(
                value = textState,
                onValueChange = { textState = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            if (uiState is PostMessageUiState.Failure) {
                val reason = uiState.reason
                val failureReason = if (reason is FailureReason.NotAuthenticated) {
                    stringResource(R.string.snack_post_message_requires_auth)
                } else {
                    stringResource(R.string.snack_post_message_failed)
                }
                Text(
                    text = failureReason,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            val isSuccessOrLoading =
                uiState is PostMessageUiState.Success || uiState is PostMessageUiState.Loading

            BottlingButton(
                text = R.string.btnPostMessage,
                enabled = !isSuccessOrLoading,
                onTapHandler = { wrappedPostHandler() },
            )
        }

    }
}

@Preview
@Composable
fun PostViewPreviewIdle() {
    PostMessageScreen({}, PostMessageUiState.Idle, onPostHandler = {})
}

@Preview
@Composable
fun PostViewPreviewFailure() {
    PostMessageScreen({}, PostMessageUiState.Failure(FailureReason.NotAuthenticated), onPostHandler = {})
}

@Preview
@Composable
fun PostViewPreviewSuccess() {
    PostMessageScreen({}, PostMessageUiState.Success(MessageEntity("", "")), onPostHandler = {})
}