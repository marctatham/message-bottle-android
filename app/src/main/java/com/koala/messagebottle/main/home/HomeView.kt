package com.koala.messagebottle.main.home

import CustomBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R
import com.koala.messagebottle.common.compose.fakeClickHandler

@Composable
fun HomeView(
    onSignInHandler: () -> Unit,
    onGetMessageHandler: () -> Unit,
    onPostMessageHandler: () -> Unit,
    onViewMessageHandler: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_in_bottle))

    CustomBox(
        canvasContent = {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                speed = 0.6f,
                modifier = Modifier.fillMaxSize()
            )
        },
        outsideCanvasContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Button(onClick = onSignInHandler) { Text(text = "Sign In") }

                Button(onClick = onGetMessageHandler) { Text(text = "Get a message from a bottle") }

                Button(onClick = onPostMessageHandler) { Text(text = "Put a message in a bottle") }

                Button(onClick = onViewMessageHandler) { Text(text = "View all messages") }
            }
        }
    )
}

@Preview
@Composable
fun HomeViewPreview() {
    HomeView(
        fakeClickHandler,
        fakeClickHandler,
        fakeClickHandler,
        fakeClickHandler
    )
}