package com.koala.messagebottle.main.getmessage

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R

// TODO: this works well, make it clean
//       - also important to chop it up
//       - also important to make the preview modes work better!
//       - investigate this issue of the animation not restarting
@Composable
fun GetMessageView(
    viewModel: GetMessageViewModel = hiltViewModel(),
) {
    val uiState: MessageUiState by viewModel.state.collectAsStateWithLifecycle()
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

    Box(modifier = Modifier.fillMaxSize()) {
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
                Text(
                    text = (uiState as MessageUiState.MessageReceived).messageEntity.message,
                    color = Color.White.copy(alpha = alpha),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .alpha(alpha)
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

            Button(
                onClick = viewModel::getNewMessage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .alpha(alpha)
            ) { Text(text = "Get another message from a bottle") }
        }
    }
}
