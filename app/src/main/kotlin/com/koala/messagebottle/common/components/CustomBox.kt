package com.koala.messagebottle.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.koala.messagebottle.common.ui.fakeChildComposable

@Preview
@Composable
fun CustomBox(
    canvasContent: @Composable () -> Unit = fakeChildComposable,
    outsideCanvasContent: @Composable () -> Unit = fakeChildComposable
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp: Dp = configuration.screenHeightDp.dp
    val radius: Dp = screenHeightDp * 0.5f  // Calculate the radius as 60% of screen height
    val yOffset: Dp =
        screenHeightDp * 0.1f  // push it up a bit so that top box = 60%, bottom box = 40%
    val colorSurfaceVariant = MaterialTheme.colorScheme.surfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .drawBehind {
                drawCircle(
                    center = Offset(x = size.width / 2f, y = yOffset.toPx()),
                    color = colorSurfaceVariant,
                    radius = radius.toPx()
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        // Displays the canvasContent within the canvas
        Box(
            modifier = Modifier.fillMaxHeight(0.6f)
        ) { canvasContent() }

        // Display the remaining content outside the canvas space
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            outsideCanvasContent()
        }
    }
}

@Preview
@Composable
fun CustomBoxPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        CustomBox(
            canvasContent = {
                Box(
                    modifier = Modifier.fillMaxSize()
                )
            },
            outsideCanvasContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                )
            }
        )
    }
}
