package com.koala.messagebottle.main.postmessage.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.EducationalScreen

@Composable
fun LoginRequiredToPostScreen(
    onProceedHandler: () -> Unit,
    onCancelHandler: () -> Unit,
    onBackHandler: () -> Unit,
) {
    EducationalScreen(
        image = Icons.Default.Security,
        imageDescription = R.string.login_required_image_description,
        title = R.string.login_required_title,
        description = R.string.login_required_description,
        onBackHandler = onBackHandler,
        onPrimaryTapHandler = onProceedHandler,
        primaryButtonText = R.string.login_required_button_primary,
        secondaryButtonText = R.string.login_required_button_secondary,
        onSecondaryTapHandler = onCancelHandler,
    )
}

@Preview
@Composable
private fun LoginRequiredToPostScreenPreview() {
    LoginRequiredToPostScreen(
        onBackHandler = { },
        onProceedHandler = { },
        onCancelHandler = { },
    )
}
