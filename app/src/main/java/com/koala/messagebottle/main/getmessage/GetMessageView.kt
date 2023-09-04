package com.koala.messagebottle.main.getmessage

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R
import com.koala.messagebottle.common.compose.fakeClickHandler
import timber.log.Timber

@Composable
fun GetMessageView(
    viewModel: GetMessageViewModel = hiltViewModel(),
) {
    val uiState: MessageState by viewModel.state.collectAsStateWithLifecycle()
    return when (uiState) {
        MessageState.Failure -> GetMessageFailedView(onNewMessageHandler = viewModel::getNewMessage)

        MessageState.Loading -> FullScreenLoadingView()

        is MessageState.MessageReceived -> GetMessageSuccessView(
            message = (uiState as MessageState.MessageReceived).messageEntity.message,
            onNewMessageHandler = viewModel::getNewMessage
        )
    }
}

@Preview
@Composable
fun FullScreenLoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize(0.35f)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
    }
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
    message: String = "Hello world!",
    onNewMessageHandler: () -> Unit = fakeClickHandler
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_from_bottle))
    val progress: Float by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    val isTextVisible: Boolean = progress == 1f

    // Animate the alpha property from 0 to 1 when isVisible is true
    val alpha: Float by animateFloatAsState(
        //targetValue = 1f, // else 0f,
        targetValue = if (isTextVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000, // Animation duration
            easing = FastOutSlowInEasing // Easing function
        ), label = "Text Fade In"
    )

    Timber.d("AlphaDebug Alpha: $alpha")

    Box(modifier = Modifier.fillMaxSize()) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize(),
        )

        // Text view that becomes visible after Lottie animation completion
        if (progress == 1f) {
            Text(
                text = message,
                color = Color.White.copy(alpha = alpha),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .alpha(alpha)
            )
        }

        Button(
            onClick = onNewMessageHandler,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) { Text(text = "Get another message from a bottle") }
    }
}
