package com.koala.messagebottle.app.postmessage.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.koala.messagebottle.R
import com.koala.messagebottle.app.postmessage.domain.MINIMUM_MESSAGE_LENGTH


@Composable
fun BottlingFailureReason(
    failure: PostMessageUiState.Failure,
) {
    val failureReason:String = when (failure.reason) {
        FailureReason.InsufficientLength -> stringResource(R.string.snack_post_message_insufficient_length, MINIMUM_MESSAGE_LENGTH)
        FailureReason.NotAuthenticated -> stringResource(R.string.snack_post_message_requires_auth)
        FailureReason.Unknown -> stringResource(R.string.snack_post_message_failed)
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

@Preview
@Composable
private fun BottlingFailureReasonPreview() {
    BottlingFailureReason(
        PostMessageUiState.Failure(FailureReason.InsufficientLength)
    )
}
