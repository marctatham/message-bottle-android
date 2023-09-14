package com.koala.messagebottle.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class BottlingButtonType {
    PRIMARY, SECONDARY
}

@Composable
fun BottlingButton(
    text: String,
    modifier: Modifier = Modifier,
    buttonType: BottlingButtonType = BottlingButtonType.PRIMARY,
    onTapHandler: () -> Unit = {},
) {
    val colors: ButtonColors = deriveButtonColors(buttonType = buttonType)

    Button(
        onClick = onTapHandler,
        modifier = modifier
            .fillMaxWidth(),
        colors = colors,
        shape = MaterialTheme.shapes.extraSmall,
    ) { Text(text = text) }
}

@Composable
private fun deriveButtonColors(buttonType: BottlingButtonType): ButtonColors {
    return when (buttonType) {
        BottlingButtonType.PRIMARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        )

        BottlingButtonType.SECONDARY -> ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        )
    }
}

@Preview
@Composable
private fun BottlingButtonPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        Spacer(modifier = Modifier.weight(1f))
        BottlingButton(
            text = "Primary Button",
            buttonType = BottlingButtonType.PRIMARY
        )

        BottlingButton(
            text = "Secondary Button",
            buttonType = BottlingButtonType.SECONDARY
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
