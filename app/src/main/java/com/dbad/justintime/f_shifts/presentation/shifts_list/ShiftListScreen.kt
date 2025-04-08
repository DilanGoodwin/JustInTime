package com.dbad.justintime.f_shifts.presentation.shifts_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme

// Stateless
@Composable
fun ShiftListScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // TODO move into state

    ModalNavigationDrawer(
        drawerContent = { FilterDraw() },
        drawerState = drawerState,
        gesturesEnabled = false
    ) {
        Scaffold(
            topBar = { ShiftTopAppBar() },
            bottomBar = { ShiftBottomNavBar() },
            floatingActionButton = { ShiftFAB() },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                MonthTitleCard()
                CalendarView()
                Spacer(modifier = Modifier.height(height = 10.dp))
                ShiftListView()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "Calendar") },
        navigationIcon = {
            // Side Menu for filters
            IconButton(onClick = {}) { //TODO toggle draw open or close value
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "") //TODO
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ShiftBottomNavBar() {
    NavigationBar {
        // Calendar Page
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(imageVector = Icons.Filled.DateRange, contentDescription = "") }, //TODO
            label = { Text(text = "Calendar") } //TODO
        )

        // Profile Page
        NavigationBarItem(
            selected = false,
            onClick = {}, //TODO
            icon = { Icon(imageVector = Icons.Filled.Approval, contentDescription = "") }, //TODO
            label = { Text(text = stringResource(R.string.profile)) }
        )
    }
}

@Composable
fun ShiftFAB() {
    SmallFloatingActionButton(onClick = {}) { //TODO
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")//TODO
    }
}

// Side sheet for users to change what is being displayed on the screen
@Composable
fun FilterDraw() {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(all = 10.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(text = "Filters")
            CreateCheckBox(checkVal = true, checkName = "Shifts") //TODO
            CreateCheckBox(checkVal = true, checkName = "Holiday") //TODO
            CreateCheckBox(checkVal = true, checkName = "Unavailability") //TODO
        }
    }
}

@Composable
fun CreateCheckBox(checkVal: Boolean, checkName: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checkVal, onCheckedChange = {}) //TODO
        Text(text = checkName)
    }
}

@ViewingSystemThemes
@Composable
fun PreviewShiftListScreen() {
    JustInTimeTheme { ShiftListScreen() }
}