package com.dbad.justintime.f_shifts.presentation.shifts_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun ShiftListScreen(){
    Column {
        MonthTitleCard()
        CalendarView()
        Spacer(modifier = Modifier.height(height = 5.dp))
        ShiftListView()
    }
}

@ViewingSystemThemes
@Composable
fun PreviewShiftListScreen(){
    JustInTimeTheme { ShiftListScreen() }
}