package com.koala.messagebottle.main.postmessage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.EducationalScreen

@Preview
@Composable
fun LoginRequiredToPostScreen() {
    EducationalScreen(
        image = Icons.Default.Security,
        imageDescription = R.string.login_required_image_description,
        title = R.string.login_required_title,
        description = R.string.login_required_description,
        onBackHandler = {},
        onPrimaryTapHandler = {},
        primaryButtonText = R.string.login_required_button_primary,
        secondaryButtonText = R.string.login_required_button_secondary,
        onSecondaryTapHandler = {},
    )
}
