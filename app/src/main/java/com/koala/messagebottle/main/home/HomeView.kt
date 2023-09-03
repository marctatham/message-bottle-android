package com.koala.messagebottle.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R

@Composable
fun HomeView(
    onSignInHandler: () -> Unit,
    onGetMessageHandler: () -> Unit,
    onPostMessageHandler: () -> Unit,
    onViewMessageHandler: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_in_bottle))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            speed = 0.7f,
            modifier = Modifier
                .fillMaxWidth() // Occupy full width
                .weight(1f) // Take as much space as possible
                .height(200.dp) // Set a fixed height
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(2f)
        ) {
            Button(onClick = onSignInHandler) { Text(text = "Sign In") }

            Button(onClick = onGetMessageHandler) { Text(text = "Get a message from a bottle") }

            Button(onClick = onPostMessageHandler) { Text(text = "Put a message in a bottle") }

            Button(onClick = onViewMessageHandler) { Text(text = "View all messages") }
        }
    }
}



@Preview
@Composable
fun HomeViewPreview() {
    val fakeFunctionImplementation: () -> Unit = { println("I'm a fake function to satisfy preview requirements") }
    HomeView(
        fakeFunctionImplementation,
        fakeFunctionImplementation,
        fakeFunctionImplementation,
        fakeFunctionImplementation
    )
}