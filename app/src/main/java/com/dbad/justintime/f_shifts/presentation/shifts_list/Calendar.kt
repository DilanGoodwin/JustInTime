package com.dbad.justintime.f_shifts.presentation.shifts_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun MonthTitleCard() {
    Column {
        Row {
            // Go back a month
            IconButton(onClick = {}) { //TODO
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "" //TODO
                )
            }

            // Current Month
            Text(
                text = "Month", //TODO
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(weight = 1f)
                    .align(alignment = Alignment.CenterVertically)
            )

            // Go forward a month
            IconButton(onClick = {}) { //TODO
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "" //TODO
                )
            }
        }
    }
}

@Composable
fun CalendarView() {
    Column {
        // Generate Heading for Days of Week
        Row {
            repeat(times = 7) {
                Box(modifier = Modifier.weight(weight = 1f)) {
                    CalendarIndividualDays(day = "Mon") //TODO
                }
            }
        }

        // Generate Dates within Month
        Column {
            repeat(times = 6) { // Weeks
                Row {
                    repeat(times = 7) { // Days
                        Box(modifier = Modifier.weight(weight = 1f)) {
                            CalendarIndividualDays(day = "1") //TODO
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarIndividualDays(
    day: String,
    daySelected: Boolean = false
) {
    // Decide whether date highlighted
    val backgroundColor = if (daySelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.background
    }

    // Generate date item
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .background(color = backgroundColor, shape = CircleShape)
                .align(alignment = Alignment.Center)
                .padding(all = 5.dp)
        )
    }
}

@ViewingSystemThemes
@Composable
fun PreviewCalendarView() {
    JustInTimeTheme { CalendarView() }
}

@ViewingSystemThemes
@Composable
fun PreviewMonthTitleCard() {
    JustInTimeTheme { MonthTitleCard() }
}