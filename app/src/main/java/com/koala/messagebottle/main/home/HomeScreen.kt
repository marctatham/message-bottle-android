package com.koala.messagebottle.main.home

import CustomBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.BottlingButton
import com.koala.messagebottle.common.components.BottlingButtonType

@Composable
fun HomeScreen(
    onGetMessageHandler: () -> Unit,
    onPostMessageHandler: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_in_bottle))

    CustomBox(
        canvasContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    speed = 0.6f,
                    modifier = Modifier.fillMaxSize(0.6f)
                )

                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = stringResource(id = R.string.home_description),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        },
        outsideCanvasContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                BottlingButton(
                    text = R.string.home_button_get_message,
                    buttonType = BottlingButtonType.PRIMARY,
                    onTapHandler = onGetMessageHandler,
                )

                BottlingButton(
                    text = R.string.home_button_put_message,
                    buttonType = BottlingButtonType.SECONDARY,
                    onTapHandler = onPostMessageHandler,
                )
            }
        }
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen({}, {})
}