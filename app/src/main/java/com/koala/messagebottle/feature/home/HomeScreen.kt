package com.koala.messagebottle.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.BottlingButton
import com.koala.messagebottle.common.components.BottlingButtonType
import com.koala.messagebottle.common.components.CustomBox


@Composable
fun HomeScreen(
    onGetMessageHandler: () -> Unit,
    onPostMessageHandler: () -> Unit,
    onViewMessagesHandler: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeUiState: HomeUiState by viewModel.state.collectAsStateWithLifecycle()
    val composition: LottieComposition? by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_in_bottle))
    HomeScreen(
        onGetMessageHandler = onGetMessageHandler,
        onPostMessageHandler = onPostMessageHandler,
        onViewMessagesHandler = onViewMessagesHandler,
        homeUiState = homeUiState,
        composition = composition
    )
}

@Composable
private fun HomeScreen(
    onGetMessageHandler: () -> Unit,
    onPostMessageHandler: () -> Unit,
    onViewMessagesHandler: () -> Unit,
    homeUiState: HomeUiState,
    composition: LottieComposition?
) {
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
                    modifier = Modifier
                        .padding(16.dp)
                        .offset(x = 0.dp, y = (-100).dp),
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        outsideCanvasContent = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp)
            ) {
                if (homeUiState is HomeUiState.Admin) {
                    Spacer(modifier = Modifier.weight(1f))
                    BottlingButton(
                        text = R.string.home_button_view_messages,
                        buttonType = BottlingButtonType.SECONDARY,
                        onTapHandler = onViewMessagesHandler,
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 8.dp
                        )
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                        ),
                        text = stringResource(id = R.string.home_description),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                BottlingButton(
                    text = R.string.home_button_get_message,
                    buttonType = BottlingButtonType.PRIMARY,
                    onTapHandler = onGetMessageHandler,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                BottlingButton(
                    text = R.string.home_button_put_message,
                    buttonType = BottlingButtonType.SECONDARY,
                    onTapHandler = onPostMessageHandler,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 16.dp
                    )
                )
            }
        }
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val composition: LottieComposition? by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.message_in_bottle))
    HomeScreen(
        onGetMessageHandler = {},
        onPostMessageHandler = {},
        onViewMessagesHandler = {},
        homeUiState = HomeUiState.Normal,
        composition = composition
    )
}