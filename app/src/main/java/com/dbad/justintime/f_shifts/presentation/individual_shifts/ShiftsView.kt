package com.dbad.justintime.f_shifts.presentation.individual_shifts

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.dbad.justintime.f_local_db.domain.model.Event
import com.dbad.justintime.f_shifts.presentation.util.formatTimeString
import com.dbad.justintime.ui.theme.JustInTimeTheme

// Stateless
@Composable
fun ShiftListView(state: ShiftState, onEvent: (ShiftEvents) -> Unit) {
    Column {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.events),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        LazyColumn {
            items(state.shifts) { event ->
                IndividualShift(event = event, onClick = { onEvent(ShiftEvents.SelectEvent(it)) })
            }
            items(state.holiday + state.unavailability) { event ->
                var personRequest = ""
                for (person in state.people) {
                    if (person.employeeUid in Event.convertStringEmployees(employees = event.employees)) {
                        personRequest = person.name
                    }
                }
                IndividualEvents(
                    event = event,
                    person = personRequest,
                    onClick = { onEvent(ShiftEvents.SelectEvent(it)) })
            }
        }

        DetailedShiftView(state = state, onEvent = onEvent)
    }
}

@ViewingSystemThemes
@Composable
fun PreviewShiftListView() {
    JustInTimeTheme { ShiftListView(state = ShiftState(), onEvent = {}) }
}

// Stateless
@Composable
fun IndividualShift(event: Event, onClick: (Event) -> Unit) {
    // Interaction Source for events
    val source = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
            .clickable(onClick = { onClick(event) })
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(space = 10.dp)) {
                LabelledTextInputFields(
                    currentValue = event.location,
                    placeHolderText = stringResource(R.string.location),
                    onValueChange = { onClick(event) },
                    readOnly = true,
                    interactionSource = source,
                    modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                )
                LabelledTextInputFields(
                    currentValue = event.rolePosition,
                    placeHolderText = stringResource(R.string.role),
                    onValueChange = { onClick(event) },
                    readOnly = true,
                    interactionSource = source,
                    modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(space = 10.dp)) {

                LabelledTextInputFields(
                    currentValue = event.startDate,
                    placeHolderText = stringResource(R.string.date),
                    onValueChange = { onClick(event) },
                    readOnly = true,
                    interactionSource = source,
                    modifier = Modifier.fillMaxWidth()
                )

                LabelledTextInputFields(
                    currentValue = "${formatTimeString(event.startTime)} - ${formatTimeString(event.endTime)}",
                    placeHolderText = stringResource(R.string.time),
                    onValueChange = { onClick(event) },
                    readOnly = true,
                    interactionSource = source,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (source.collectIsPressedAsState().value) onClick(event)
}

// Stateless
@Composable
fun IndividualEvents(event: Event, person: String, onClick: (Event) -> Unit) {
    // Interaction Source for events
    val source = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .padding(all = 5.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 5.dp))
            .clickable(onClick = { onClick(event) })
    ) {
        Column(
            modifier = Modifier
                .padding(all = 5.dp)
                .clickable(onClick = { onClick(event) })
        )
        {
            LabelledTextInputFields(
                currentValue = stringResource(event.type.stringVal),
                placeHolderText = stringResource(R.string.request_type),
                onValueChange = { onClick(event) },
                readOnly = true,
                interactionSource = source,
                modifier = Modifier
                    .fillMaxWidth()
            )

            LabelledTextInputFields(
                currentValue = person,
                placeHolderText = "Person",
                onValueChange = { onClick(event) },
                readOnly = true,
                interactionSource = source,
                modifier = Modifier
                    .fillMaxWidth()
            )

            LabelledTextInputFields(
                currentValue = "${event.startDate} - ${event.endDate}",
                placeHolderText = stringResource(R.string.date),
                onValueChange = { onClick(event) },
                readOnly = true,
                interactionSource = source,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

    if (source.collectIsPressedAsState().value) onClick(event)

}

@ViewingSystemThemes
@Composable
fun PreviewIndividualShift() {
    JustInTimeTheme { IndividualShift(event = Event(), onClick = {}) }
}