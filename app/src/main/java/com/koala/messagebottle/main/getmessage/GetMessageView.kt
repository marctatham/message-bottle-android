package com.koala.messagebottle.main.getmessage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R
import com.koala.messagebottle.common.compose.fakeClickHandler

@Composable
fun GetMessageView(
    viewModel: GetMessageViewModel = hiltViewModel(),
) {
    val uiState: MessageState by viewModel.state.collectAsStateWithLifecycle()
    return when (uiState) {
        MessageState.Failure -> GetMessageFailedView(onNewMessageHandler = viewModel::getNewMessage)

        MessageState.Loading -> FullScreenLoadingView()

        is MessageState.MessageReceived -> GetMessageSuccessView(onNewMessageHandler = viewModel::getNewMessage)
    }
}

@Preview
@Composable
fun FullScreenLoadingView() {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxSize(0.4f),
        color = MaterialTheme.colorScheme.surfaceVariant,
        trackColor = MaterialTheme.colorScheme.secondary,
    )
}

@Preview
@Composable
fun GetMessageFailedView(
    onNewMessageHandler: () -> Unit = fakeClickHandler
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = android.R.drawable.stat_notify_error),
            contentDescription = null,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(0.4f)
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        Button(
            onClick = onNewMessageHandler,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Try again")
        }
    }
}

@Preview
@Composable
fun GetMessageSuccessView(
    onNewMessageHandler: () -> Unit = fakeClickHandler
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_from_bottle))

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize(),
        )

        // TODO: introduce text view to display message

        Button(
            onClick = onNewMessageHandler,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) { Text(text = "Get another message from a bottle") }
    }
}
