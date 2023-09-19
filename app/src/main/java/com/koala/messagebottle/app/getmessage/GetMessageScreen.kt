package com.koala.messagebottle.app.getmessage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    GetMessageView(onBackHandler = onBackHandler, uiState = uiState)
}

@Composable
private fun GetMessageView(
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
    val lottieVisible = !isAnimationComplete
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        BottlingAppBar(
            onBackHandler = onBackHandler,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        AnimatedVisibility(
            visible = lottieVisible,
            enter = fadeIn(animationSpec = tween(2000, 0, FastOutSlowInEasing)),
            exit = fadeOut(animationSpec = tween(2000, 0, FastOutSlowInEasing))
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.fillMaxSize()
            )
        }

        AnimatedVisibility(
            visible = isAnimationComplete,
            enter = fadeIn(animationSpec = tween(2000, 0, FastOutSlowInEasing)),
            exit = fadeOut(animationSpec = tween(2000, 0, FastOutSlowInEasing))
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                if (isAnimationComplete) {
                    if (uiState is MessageUiState.MessageReceived) {
                        UnbottledMessageCard(
                            unbottledMessage = uiState.messageEntity.message,
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Center)
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
                            .padding(16.dp)
                            .align(Alignment.BottomCenter)
                    )
                }

            }
        }
    }
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
