package com.koala.messagebottle.app.postmessage.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.BottlingButton
import com.koala.messagebottle.common.messages.domain.MessageEntity

@Composable
fun PostScreen(
    viewModel: PostMessageViewModel = hiltViewModel(),
) {
    val state: PostMessageUiState by viewModel.state.collectAsStateWithLifecycle()
    val onPostHandler: (messageToPost: String) -> Unit = { viewModel.postMessage(it) }
    PostView(state, onPostHandler)
}

@Composable
fun PostView(
    uiState: PostMessageUiState,
    onPostHandler: (messageToPost: String) -> Unit,
) {
    var textState: String by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))

        if (uiState is PostMessageUiState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                strokeWidth = 4.dp
            )
        } else {
            BottlingMessageCard(value = textState, onValueChange = { textState = it })
        }

        Spacer(modifier = Modifier.weight(1f))

        // conditionally display failure reason
        if (uiState is PostMessageUiState.Failure) {
            val reason = uiState.reason
            val failureReason = if (reason is FailureReason.NotAuthenticated) {
                stringResource(R.string.snack_post_message_requires_auth)
            } else {
                stringResource(R.string.snack_post_message_failed)
            }
            Text(
                text = failureReason,
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
            )
        }

        val isSuccessOrLoading = uiState is PostMessageUiState.Success || uiState is PostMessageUiState.Loading
        BottlingButton(
            text = R.string.btnPostMessage,
            enabled = !isSuccessOrLoading,
            modifier = Modifier.padding(16.dp),
            onTapHandler = { onPostHandler(textState) },
        )
    }
}

@Composable
private fun BottlingMessageCard(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            text = stringResource(id = R.string.title_post_message),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 16.dp),
            onValueChange = onValueChange,
            value = value,
            maxLines = 15,

            placeholder = { Text(text = stringResource(R.string.post_message_hint)) },
        )
    }
}

@Preview
@Composable
private fun BottlingMessageCard() {
    BottlingMessageCard(
        value = "This is my message that I'm going to be bottling",
        onValueChange = {},
    )
}

@Preview
@Composable
fun PostViewPreviewIdle() {
    PostView(PostMessageUiState.Idle, onPostHandler = {})
}

@Preview
@Composable
fun PostViewPreviewFailure() {
    PostView(PostMessageUiState.Failure(FailureReason.NotAuthenticated), onPostHandler = {})
}

@Preview
@Composable
fun PostViewPreviewSuccess() {
    PostView(PostMessageUiState.Success(MessageEntity("", "")), onPostHandler = {})
}
