package com.dbad.justintime.f_login_register.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.TopStart, modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                val logoImage = if (isSystemInDarkTheme()) {
                    painterResource(R.drawable.justintime__darkmode)
                } else {
                    painterResource(R.drawable.justintime_lightmode)
                }
                Image(
                    painter = logoImage,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(250.dp)
                )
            }

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp)) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text = stringResource(R.string.email)) },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(size = 8.dp))
                            .width(400.dp)
                            .height(60.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text(text = stringResource(R.string.password)) },
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(6.dp))
                            .width(400.dp)
                            .height(60.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .width(120.dp)
                            .height(60.dp)
                    ) {
                        Text(text = stringResource(R.string.register))
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .width(120.dp)
                            .height(60.dp)
                    ) {
                        Text(text = stringResource(R.string.login))
                    }
                }
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun LoginScreenPreview() {
    JustInTimeTheme { LoginScreen() }
}