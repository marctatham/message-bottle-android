package com.koala.messagebottle.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class BottlingButtonType {
    PRIMARY, SECONDARY
}

@Composable
fun BottlingButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonType: BottlingButtonType = BottlingButtonType.PRIMARY,
    onTapHandler: () -> Unit = {},
) = BottlingButton(
    text = stringResource(text),
    enabled = enabled,
    isLoading = isLoading,
    modifier = modifier,
    buttonType = buttonType,
    onTapHandler = onTapHandler,
)

@Composable
private fun BottlingButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonType: BottlingButtonType = BottlingButtonType.PRIMARY,
    onTapHandler: () -> Unit = {},
) {
    return when (buttonType) {
        BottlingButtonType.PRIMARY -> BottlingButtonPrimary(
            text = text,
            modifier = modifier,
            enabled = enabled,
            isLoading = isLoading,
            buttonType = buttonType,
            onTapHandler = onTapHandler
        )

        BottlingButtonType.SECONDARY -> BottlingButtonSecondary(
            text = text,
            modifier = modifier,
            enabled = enabled,
            isLoading = isLoading,
            buttonType = buttonType,
            onTapHandler = onTapHandler
        )
    }
}

@Composable
private fun BottlingButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonType: BottlingButtonType = BottlingButtonType.PRIMARY,
    onTapHandler: () -> Unit = {},
) {
    Button(
        onClick = onTapHandler,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        content = { ButtonContent(text, isLoading, buttonType) }
    )
}

@Composable
private fun BottlingButtonSecondary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    buttonType: BottlingButtonType = BottlingButtonType.PRIMARY,
    onTapHandler: () -> Unit = {},
) {
    OutlinedButton(
        onClick = onTapHandler,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        content = { ButtonContent(text, isLoading, buttonType) }
    )
}


@Composable
private fun ButtonContent(
    text: String,
    isLoading: Boolean = false,
    buttonType: BottlingButtonType = BottlingButtonType.PRIMARY,
) {
    Row {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            minLines = 1,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
        )
        if (isLoading) {
            CircularProgressIndicator(
                strokeWidth = 4.dp,
                color = deriveIndicatorColor(buttonType),
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
private fun deriveIndicatorColor(buttonType: BottlingButtonType): Color = when (buttonType) {
    BottlingButtonType.PRIMARY -> MaterialTheme.colorScheme.onPrimary
    BottlingButtonType.SECONDARY -> MaterialTheme.colorScheme.onSecondary
}

@Preview
@Composable
private fun BottlingButtonPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        BottlingButton(
            text = "Primary Button",
            buttonType = BottlingButtonType.PRIMARY
        )
        Spacer(modifier = Modifier.height(16.dp))
        BottlingButton(
            text = "Secondary Button",
            buttonType = BottlingButtonType.SECONDARY
        )
        Spacer(modifier = Modifier.height(16.dp))
        BottlingButton(
            text = "Primary Button - Loading",
            buttonType = BottlingButtonType.PRIMARY,
            isLoading = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        BottlingButton(
            text = "Secondary Button - Loading",
            buttonType = BottlingButtonType.SECONDARY,
            isLoading = true
        )
    }
}
