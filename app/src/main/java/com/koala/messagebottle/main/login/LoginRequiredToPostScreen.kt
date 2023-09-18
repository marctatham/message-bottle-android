package com.koala.messagebottle.main.login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.koala.messagebottle.R
import com.koala.messagebottle.common.components.EducationalScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginRequiredToPostScreen(
    onProceedHandler: () -> Unit,
    onCancelHandler: () -> Unit,
    onBackHandler: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet: Boolean by remember { mutableStateOf(false) }

    val bottomSheetExpand: () -> Unit = { showBottomSheet = true }
    val bottomSheetCollapse: () -> Unit = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet = false
            }
        }
    }

    EducationalScreen(
        image = Icons.Default.AccountCircle,
        imageDescription = R.string.login_required_image_description,
        title = R.string.login_required_title,
        description = R.string.login_required_description,
        onBackHandler = onBackHandler,
        onPrimaryTapHandler = bottomSheetExpand,
        primaryButtonText = R.string.login_required_button_primary,
        secondaryButtonText = R.string.login_required_button_secondary,
        onSecondaryTapHandler = onCancelHandler,
    )

    if (showBottomSheet) {
        LoginBottomSheet(
            onDismissRequest = bottomSheetCollapse,
            sheetState = sheetState,
            onSignInCompleteHandler = onProceedHandler
        )
    }
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
