package com.dbad.justintime.f_shifts.presentation.shifts_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.f_shifts.presentation.individual_shifts.IndividualShift
import com.dbad.justintime.ui.theme.JustInTimeTheme

// Stateless
@Composable
fun ShiftListView() {
    Column() {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Events", //TODO
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            repeat(times = 5) {
                IndividualShift()
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun PreviewShiftListView() {
    JustInTimeTheme { ShiftListView() }
}

