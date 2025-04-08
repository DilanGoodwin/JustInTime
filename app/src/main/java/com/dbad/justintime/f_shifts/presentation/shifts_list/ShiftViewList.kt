package com.dbad.justintime.f_shifts.presentation.shifts_list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.LabelledTextInputFields
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
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

// Stateless
@Composable
fun IndividualShift() {
    Box(
        modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(space = 10.dp)) {
                LabelledTextInputFields(
                    currentValue = "", //TODO
                    placeHolderText = stringResource(R.string.location),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                )
                LabelledTextInputFields(
                    currentValue = "", //TODO
                    placeHolderText = stringResource(R.string.role),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(space = 10.dp)) {
                LabelledTextInputFields(
                    currentValue = "", //TODO
                    placeHolderText = stringResource(R.string.date),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                LabelledTextInputFields(
                    currentValue = "", //TODO
                    placeHolderText = stringResource(R.string.time),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun PreviewShiftListView() {
    JustInTimeTheme { ShiftListView() }
}

@ViewingSystemThemes
@Composable
fun PreviewIndividualShift() {
    JustInTimeTheme { IndividualShift() }
}