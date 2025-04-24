package com.dbad.justintime.f_shifts.presentation.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme
import java.time.DayOfWeek
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarView(calendarState: CalendarState, calendarEvents: (CalendarEvents) -> Unit) {
    Column {
        // Month Title Section & Controls
        Row {
            // Go back a month
            IconButton(onClick = { calendarEvents(CalendarEvents.MonthGoBack) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.previous_month)
                )
            }

            // Current Month
            val currentMonthTextString = Month.of(calendarState.currentMonth)
                .getDisplayName(
                    TextStyle.FULL,
                    Locale.getDefault()
                ) + " ${calendarState.currentYear}"

            Text(
                text = currentMonthTextString,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .weight(weight = 1f)
                    .align(alignment = Alignment.CenterVertically)
                    .clickable(onClick = { calendarEvents(CalendarEvents.ReturnCurrentMonth) })
            )

            // Go forward a month
            IconButton(onClick = { calendarEvents(CalendarEvents.MonthGoForward) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(R.string.next_month)
                )
            }
        }

        // Generate Heading for Days of Week
        Row {
            for (day in DayOfWeek.entries) {
                Box(modifier = Modifier.weight(weight = 1f)) {
                    CalendarIndividualDays(
                        day = day.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        ), enabled = false
                    )
                }
            }
        }

        // Generate Dates within Month
        Column {
            var iterator = 0
            while (iterator < calendarState.daysWithinMonth.size) { // Weeks
                Row {
                    for (i in 1..7) { // Days
                        Box(modifier = Modifier.weight(weight = 1f)) {
                            if (calendarState.daysWithinMonth.size <= iterator || calendarState.daysWithinMonth[iterator] == 0) {
                                CalendarIndividualDays(day = "", enabled = false)
                            } else {
                                CalendarIndividualDays(
                                    day = calendarState.daysWithinMonth[iterator].toString(),
                                    daySelected = (calendarState.daysWithinMonth[iterator] == calendarState.selectedDate),
                                    onClick = { calendarEvents(CalendarEvents.SelectADay(day = it)) }
                                )
                            }
                        }
                        iterator++
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarIndividualDays(
    day: String,
    onClick: (Int) -> Unit = {},
    enabled: Boolean = true,
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
        TextButton(
            onClick = { onClick(day.toInt()) },
            shape = CircleShape,
            colors = ButtonColors(
                containerColor = backgroundColor,
                contentColor = ButtonDefaults.buttonColors().contentColor,
                disabledContainerColor = backgroundColor,
                disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor
            ),
            enabled = enabled
        ) {
            Text(
                text = day,
                style = MaterialTheme.typography.bodyMedium,
                color = if (daySelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .background(color = backgroundColor, shape = CircleShape)
                    .padding(all = 5.dp)
            )
        }
    }
}

@ViewingSystemThemes
@Composable
fun PreviewCalendarView() {
    JustInTimeTheme { CalendarView(calendarState = CalendarState(), calendarEvents = {}) }
}