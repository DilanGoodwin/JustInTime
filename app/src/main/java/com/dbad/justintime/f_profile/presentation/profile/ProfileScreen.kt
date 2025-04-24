package com.dbad.justintime.f_profile.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.DateSelectorDropDown
import com.dbad.justintime.core.presentation.util.DateSelectorField
import com.dbad.justintime.core.presentation.util.ExpandableCardArea
import com.dbad.justintime.core.presentation.util.LabelledTextDropDownFields
import com.dbad.justintime.core.presentation.util.LabelledTextInputFields
import com.dbad.justintime.core.presentation.util.PreferredContactField
import com.dbad.justintime.core.presentation.util.RelationField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationCompanyNameField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationContractType
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationExpandableField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationManagerNameField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationRole
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TestTagPreferredName
import com.dbad.justintime.core.presentation.util.TestTagProfileSaveButton
import com.dbad.justintime.core.presentation.util.TestTagUserInformationExpandableField
import com.dbad.justintime.core.presentation.util.TextInputField
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.f_local_db.domain.model.util.ContractType
import com.dbad.justintime.f_local_db.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_local_db.domain.model.util.Relation
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onSignOut: () -> Unit,
    onNavShiftView: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val event = viewModel::onEvent
    event(ProfileEvent.SetSignOutEvent(onSignOut))
    event(ProfileEvent.SetShiftNavEvent(onNavShiftView))

    ProfileScreen(
        state = state,
        onEvent = event,
        userEvent = viewModel::onUserEvent,
        emergencyContactEvent = viewModel::onEmergencyContactEvent,
        companyEvents = viewModel::onCompanyEvent
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
    userEvent: (ProfileUserEvents) -> Unit,
    emergencyContactEvent: (ProfileEmergencyContactEvents) -> Unit,
    companyEvents: (ProfileCompanyEvents) -> Unit
) {
    Scaffold(
        topBar = { ProfileTopBar(onEvent = onEvent) },
        bottomBar = { BottomNavBar(onEvent = onEvent) },
        floatingActionButton = {
            if (state.changeMade) {
                SmallFloatingActionButton(
                    modifier = Modifier.testTag(tag = TestTagProfileSaveButton),
                    onClick = { onEvent(ProfileEvent.SaveButton) }) {
                    Box(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = stringResource(R.string.save),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 20.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                UserUpdateFields(state = state, onEvent = userEvent)

                // Emergency Contact Area
                EmergencyContactArea(state = state, onEvent = emergencyContactEvent)

                // Company Information
                CompanyInformationArea(state = state, onEvent = companyEvents)
            }
        }
    }
}

@Composable
fun BottomNavBar(onEvent: (ProfileEvent) -> Unit) {
    NavigationBar() {
        NavigationBarItem(
            selected = false,
            onClick = { onEvent(ProfileEvent.NavigateToShiftView) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = stringResource(R.string.calendar)
                )
            },
            label = { Text(text = stringResource(R.string.calendar)) }
        )
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Filled.Approval,
                    contentDescription = stringResource(R.string.profile)
                )
            },
            label = { Text(text = stringResource(R.string.profile)) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(onEvent: (ProfileEvent) -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.profile)) },
        navigationIcon = {
            IconButton(
                onClick = { onEvent(ProfileEvent.SignOut) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = stringResource(R.string.cancel)
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun UserUpdateFields(
    state: ProfileState,
    onEvent: (ProfileUserEvents) -> Unit
) {
    ExpandableCardArea(
        isExpanded = state.expandUserInformationArea,
        expandableButtonClick = { onEvent(ProfileUserEvents.ToggleExpandedArea) },
        cardTitle = stringResource(R.string.user_information),
        testTag = TestTagUserInformationExpandableField
    ) {
        // Name Field
        TextInputField(
            currentValue = state.employee.name,
            placeHolderText = stringResource(R.string.name),
            textFieldError = state.userNameError,
            errorString = stringResource(R.string.noNameProvided),
            onValueChange = { onEvent(ProfileUserEvents.SetName(it)) },
            testingTag = TestTagNameField
        )

        // Pref Name Field
        TextInputField(
            currentValue = state.employee.preferredName,
            placeHolderText = stringResource(R.string.preferredName),
            onValueChange = { onEvent(ProfileUserEvents.SetPreferredName(it)) },
            testingTag = TestTagPreferredName
        )

        // Email Field
        TextInputField(
            currentValue = state.user.email,
            placeHolderText = stringResource(R.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { onEvent(ProfileUserEvents.SetEmail(it)) },
            textFieldError = state.userEmailError,
            errorString = stringResource(R.string.invalidEmailError),
            testingTag = TestTagEmailField
        )

        // DOB Picker
        DateSelectorField(
            currentValue = state.employee.dateOfBirth,
            placeHolderText = stringResource(R.string.dateOfBirth),
            toggleDatePicker = { onEvent(ProfileUserEvents.ToggleShowDatePicker) },
            dateError = state.dateOfBirthError,
            modifier = Modifier
                .width(width = 400.dp)
                .height(height = 80.dp)
        ) {
            DateSelectorDropDown(
                showDatePicker = state.showDateOfBirthPicker,
                saveSelectedDate = { onEvent(ProfileUserEvents.SetDateOfBirth(dateOfBirth = it)) }
            )
        }

        // Phone Number Field
        TextInputField(
            currentValue = state.employee.phone,
            placeHolderText = stringResource(R.string.phoneNumb),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { onEvent(ProfileUserEvents.SetPhoneNumb(it)) },
            textFieldError = state.userPhoneError,
            errorString = stringResource(R.string.invalidPhoneNumb),
            testingTag = TestTagPhoneNumberField
        )

        // Pref Contact Method Drop Down
        PreferredContactField(
            currentValue = stringResource(state.employee.preferredContactMethod.stringVal),
            expandDropDown = state.expandPrefContactMethod,
            dropDownToggle = { onEvent(ProfileUserEvents.TogglePrefContactDropDown) },
            selectContactMethod = { onEvent(ProfileUserEvents.SetPrefContactMethod(it)) }
        )
    }
}

@Composable
fun EmergencyContactArea(
    state: ProfileState,
    onEvent: (ProfileEmergencyContactEvents) -> Unit
) {
    ExpandableCardArea(
        isExpanded = state.expandEmergencyContactArea,
        expandableButtonClick = { onEvent(ProfileEmergencyContactEvents.ToggleExpandableArea) },
        cardTitle = stringResource(R.string.emergencyContact),
        testTag = TestTagEmergencyContactExpandableField
    ) {
        // Emergency Contact Name Field
        TextInputField(
            currentValue = state.emergencyContact.name,
            placeHolderText = stringResource(R.string.name),
            onValueChange = { onEvent(ProfileEmergencyContactEvents.SetEmergencyContactName(name = it)) },
            textFieldError = state.emergencyContactNameError,
            errorString = stringResource(R.string.noNameProvided),
            testingTag = TestTagNameField
        )

        // Emergency Contact Pref Name Field
        TextInputField(
            currentValue = state.emergencyContact.preferredName,
            placeHolderText = stringResource(R.string.preferredName),
            onValueChange = { onEvent(ProfileEmergencyContactEvents.SetEmergencyContactPrefName(name = it)) },
            testingTag = TestTagPreferredName
        )

        // Emergency Contact Phone Number Field
        TextInputField(
            currentValue = state.emergencyContact.phone,
            placeHolderText = stringResource(R.string.phoneNumb),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textFieldError = state.emergencyContactPhoneError,
            errorString = stringResource(R.string.invalidPhoneNumb),
            onValueChange = { onEvent(ProfileEmergencyContactEvents.SetEmergencyContactPhone(phone = it)) },
            testingTag = TestTagPhoneNumberField
        )

        // Emergency Contact Email Field
        TextInputField(
            currentValue = state.emergencyContact.email,
            placeHolderText = stringResource(R.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textFieldError = state.emergencyContactEmailError,
            errorString = stringResource(R.string.invalidEmailError),
            onValueChange = { onEvent(ProfileEmergencyContactEvents.SetEmergencyContactEmail(email = it)) },
            testingTag = TestTagEmailField
        )

        // Emergency Contact Pref Contact Drop Down
        PreferredContactField(
            currentValue = if (state.emergencyContact.preferredContactMethod == PreferredContactMethod.NONE) {
                ""
            } else {
                stringResource(state.emergencyContact.preferredContactMethod.stringVal)
            },
            expandDropDown = state.emergencyContactExpandPrefContactMethod,
            dropDownToggle = { onEvent(ProfileEmergencyContactEvents.ToggleEmergencyContactPrefContactDropDown) },
            selectContactMethod = {
                onEvent(
                    ProfileEmergencyContactEvents.SetEmergencyContactPrefContactMethod(contactMethod = it)
                )
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Emergency Contact Relation Drop Down
        RelationField(
            currentValue = if (state.emergencyContact.relation == Relation.NONE) {
                ""
            } else {
                stringResource(state.emergencyContact.relation.stringVal)
            },
            expandDropDown = state.emergencyContactExpandedRelation,
            dropDownToggle = { onEvent(ProfileEmergencyContactEvents.ToggleEmergencyContactRelationDropDown) },
            selectRelation = {
                onEvent(
                    ProfileEmergencyContactEvents.SetEmergencyContactRelation(relation = it)
                )
            }
        )
    }
}

@Composable
fun CompanyInformationArea(
    state: ProfileState,
    onEvent: (ProfileCompanyEvents) -> Unit
) {
    ExpandableCardArea(
        isExpanded = state.expandCompanyInformationArea,
        expandableButtonClick = { onEvent(ProfileCompanyEvents.ToggleExpandedArea) },
        cardTitle = stringResource(R.string.companyInformation),
        testTag = TestTagCompanyInformationExpandableField
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            LabelledTextInputFields(
                currentValue = state.employee.companyName,
                placeHolderText = stringResource(R.string.companyName),
                onValueChange = { onEvent(ProfileCompanyEvents.SetCompanyName(companyName = it)) },
                readOnly = !state.employee.isAdmin,
                testingTag = TestTagCompanyInformationCompanyNameField
            )

            LabelledTextInputFields(
                currentValue = state.employee.manager,
                placeHolderText = stringResource(R.string.directReport),
                onValueChange = { onEvent(ProfileCompanyEvents.SetManagerName(managerName = it)) },
                readOnly = !state.employee.isAdmin,
                testingTag = TestTagCompanyInformationManagerNameField
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                LabelledTextDropDownFields(
                    currentValue = stringResource(state.employee.contractType.stringVal),
                    placeHolderText = stringResource(R.string.contractType),
                    testTag = TestTagCompanyInformationContractType,
                    readOnly = !state.employee.isAdmin,
                    expandedDropDown = state.companyInformationExpandedContractType,
                    dropDownToggle = { onEvent(ProfileCompanyEvents.ToggleContractTypeDropDown) },
                    modifier = Modifier.width(180.dp)
                ) {
                    DropdownMenu(
                        expanded = state.companyInformationExpandedContractType,
                        onDismissRequest = { onEvent(ProfileCompanyEvents.ToggleContractTypeDropDown) }) {
                        for (method in ContractType.entries) {
                            DropdownMenuItem(
                                text = { Text(text = stringResource(method.stringVal)) },
                                onClick = {
                                    onEvent(
                                        ProfileCompanyEvents.SetContractType(contractType = method)
                                    )
                                }
                            )
                        }
                    }
                }

                LabelledTextInputFields(
                    currentValue = state.employee.role,
                    placeHolderText = stringResource(R.string.role),
                    testingTag = TestTagCompanyInformationRole,
                    readOnly = !state.employee.isAdmin,
                    onValueChange = { onEvent(ProfileCompanyEvents.SetRole(role = it)) },
                    modifier = Modifier.width(190.dp)
                )
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenPreview() {
    JustInTimeTheme {
        ProfileScreen(
            state = ProfileState(),
            onEvent = {},
            userEvent = {},
            emergencyContactEvent = {},
            companyEvents = {}
        )
    }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenUserInformationPreview() {
    JustInTimeTheme {
        UserUpdateFields(state = ProfileState(), onEvent = {})
    }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenEmergencyContactPreview() {
    JustInTimeTheme {
        EmergencyContactArea(
            state = ProfileState(expandEmergencyContactArea = true),
            onEvent = {}
        )
    }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenCompanyInformationPreview() {
    JustInTimeTheme {
        CompanyInformationArea(
            state = ProfileState(expandCompanyInformationArea = true),
            onEvent = {})
    }
}