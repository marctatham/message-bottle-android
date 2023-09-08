//import androidx.compose.foundation.Canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.koala.messagebottle.common.ui.fakeChildComposable

@Preview
@Composable
fun CustomBox(
    canvasContent: @Composable () -> Unit = fakeChildComposable,
    outsideCanvasContent: @Composable () -> Unit = fakeChildComposable
) {
    val configuration = LocalConfiguration.current
    val halfScreenHeightDp = (configuration.screenHeightDp / 2).dp
    val oneTenthOfScreenHeightDp = (configuration.screenHeightDp / 10).dp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Canvas(modifier = Modifier
            .offset(x = 0.dp, y = oneTenthOfScreenHeightDp * 1)
            .align(alignment = Alignment.CenterHorizontally),
            onDraw = {
                drawCircle(color = Color.White, radius = halfScreenHeightDp.toPx())
            })

        // Displays the canvasContent within the canvas
        Box(
            modifier = Modifier.weight(2f)
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
