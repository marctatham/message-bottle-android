package com.koala.messagebottle.main.getmessage

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.BottlingAppBar
import com.koala.messagebottle.common.components.BottlingButton
import com.koala.messagebottle.common.components.BottlingButtonType
import com.koala.messagebottle.common.messages.domain.MessageEntity

@Composable
fun GetMessageScreen(
    onBackHandler: () -> Unit,
    viewModel: GetMessageViewModel = hiltViewModel(),
) {
    GetMessageView(
        onBackHandler = onBackHandler,
        uiState = viewModel.state.collectAsStateWithLifecycle().value,
    )
}

@Composable
fun GetMessageView(
    onBackHandler: () -> Unit,
    uiState: MessageUiState,
) {
    val isPlaying = uiState is MessageUiState.PlayingAnimation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_from_bottle))
    val progress: Float by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        speed = 1f,
        restartOnPlay = true,
        iterations = 1,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
    )

    val isAnimationComplete: Boolean = progress == 1f
    val alpha: Float by animateFloatAsState(
        targetValue = if (isAnimationComplete) 1f else 0f,
        animationSpec = tween(2000, 0, FastOutSlowInEasing),
        label = "Text Fade Animation"
    )
    val inverseAlpha = 1f - alpha // Calculate the inverse value

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
    ) {

        BottlingAppBar(
            onBackHandler = onBackHandler,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .alpha(inverseAlpha),
        )

        if (isAnimationComplete) {
            if (uiState is MessageUiState.MessageReceived) {
                UnbottledMessageCard(
                    unbottledMessage = uiState.messageEntity.message,
                    alpha = alpha,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                )
            } else if (uiState is MessageUiState.Failure) {
                Image(
                    painter = painterResource(id = android.R.drawable.stat_notify_error),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }

            BottlingButton(
                text = R.string.get_message_button_acknowledge,
                buttonType = BottlingButtonType.PRIMARY,
                onTapHandler = onBackHandler,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .alpha(alpha)
            )
        }
    }
}

@Composable
private fun UnbottledMessageCard(
    unbottledMessage: String,
    alpha: Float,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = alpha)
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
        shape = MaterialTheme.shapes.extraSmall,
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = unbottledMessage,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun UnbottledMessageCardPreview(
) {
    UnbottledMessageCard(
        unbottledMessage = "This is the message I have received",
        alpha = 1f
    )
}


@Preview
@Composable
private fun GetMessageReceivedPreview(
) {
    GetMessageView(
        onBackHandler = {},
        uiState = MessageUiState.MessageReceived(MessageEntity("1", "Hello World"))
    )
}

@Preview
@Composable
private fun GetMessageAnimatingPreview(
) {
    GetMessageView(
        onBackHandler = {},
        uiState = MessageUiState.PlayingAnimation,
    )
}

@Preview
@Composable
private fun GetMessageFailedPreview(
) {
    GetMessageView(
        onBackHandler = {},
        uiState = MessageUiState.Failure
    )
}
