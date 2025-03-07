package com.dbad.justintime.f_login_register.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R

@Composable
fun JustInTimeLogoDisplay() {
    val logoImage = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.justintime__darkmode)
    } else {
        painterResource(R.drawable.justintime_lightmode)
    }
    Image(
        painter = logoImage,
        contentDescription = stringResource(R.string.logoDescription),
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(250.dp)
    )
}