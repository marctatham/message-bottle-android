package com.koala.messagebottle.feature.getmessage.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.koala.messagebottle.R

@Composable
fun UnbottledMessageCard(
    onReportHandler: () -> Unit,
    unbottledMessage: String,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp, horizontal = 0.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            FilledIconButton(
                onClick = onReportHandler,
                modifier = Modifier.padding(8.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Flag,
                    contentDescription = stringResource(id = R.string.get_message_report),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
            text = unbottledMessage,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun UnbottledMessageCardPreview(
) {
    UnbottledMessageCard(
        {},
        unbottledMessage = "This is the message I have received"
    )
}
