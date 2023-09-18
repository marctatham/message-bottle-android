package com.koala.messagebottle.app.postmessage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.koala.messagebottle.R
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
    val context = LocalContext.current

    val textState: MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue()) }
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
            Text(
                text = context.getString(R.string.title_post_message),
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                minLines = 10,
                placeholder = { Text(text = context.getString(R.string.post_message_hint)) },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // conditionally display failure reason
        if (uiState is PostMessageUiState.Failure) {
            val reason = uiState.reason
            val failureReason = if (reason is FailureReason.NotAuthenticated) {
                context.getString(R.string.snack_post_message_requires_auth)
            } else {
                context.getString(R.string.snack_post_message_failed)
            }
            Text(
                text = failureReason,
                modifier = Modifier.padding(16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error,
            )
        }

        val isSuccessOrLoading =
            uiState is PostMessageUiState.Success || uiState is PostMessageUiState.Loading

        Button(
            onClick = { onPostHandler(textState.value.text) },
            enabled = !isSuccessOrLoading,
            modifier = Modifier.padding(16.dp),
        ) { Text(text = context.getString(R.string.btnPostMessage)) }
    }
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
