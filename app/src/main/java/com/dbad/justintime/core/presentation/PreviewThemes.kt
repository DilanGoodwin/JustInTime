package com.dbad.justintime.core.presentation

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "LightMode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "DarkMode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class ViewingSystemThemes