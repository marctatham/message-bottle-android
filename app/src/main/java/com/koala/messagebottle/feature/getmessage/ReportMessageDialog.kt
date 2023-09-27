package com.koala.messagebottle.feature.getmessage

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.koala.messagebottle.R

@Composable
fun ReportMessageDialog(
    onDismiss: () -> Unit,
    onReportConfirmed: () -> Unit,
) {
    AlertDialog(onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) { Text(text = stringResource(id = R.string.get_message_report_dialog_cancel)) }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onReportConfirmed()
                    onDismiss()
                },
            ) { Text(text = stringResource(id = R.string.get_message_report_dialog_confirm)) }
        },
        title = {
            Text(
                text = stringResource(id = R.string.get_message_report_dialog_title),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
            )
        },
        text = { Text(text = stringResource(id = R.string.get_message_report_dialog_description)) })
}

@Preview
@Composable
private fun ReportMessageDialogPreview() {
    ReportMessageDialog(onReportConfirmed = {}, onDismiss = {})
}
