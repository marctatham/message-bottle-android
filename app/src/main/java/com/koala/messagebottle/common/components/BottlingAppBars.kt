package com.koala.messagebottle.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.koala.messagebottle.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottlingAppBar(
    modifier: Modifier = Modifier,
    onBackHandler: () -> Unit
) {
    TopAppBar(
        title = {},
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        navigationIcon = {
            FilledIconButton(
                onClick = onBackHandler,
                modifier = Modifier.padding(8.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.button_back),
                    modifier = Modifier.size(14.dp)
                )
            }
        },
    )
}

@Preview
@Composable
fun BottlingAppBarPreview() {
    BottlingAppBar(onBackHandler = {})
}