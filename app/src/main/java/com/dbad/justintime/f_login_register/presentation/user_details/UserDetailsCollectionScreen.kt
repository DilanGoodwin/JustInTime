package com.dbad.justintime.f_login_register.presentation.user_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.DateSelectorField
import com.dbad.justintime.core.presentation.util.DualButtonFields
import com.dbad.justintime.core.presentation.util.EmergencyContactField
import com.dbad.justintime.core.presentation.util.JustInTimeLogoDisplay
import com.dbad.justintime.core.presentation.util.PreferredContactField
import com.dbad.justintime.core.presentation.util.RelationField
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TextInputField
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_login_register.domain.model.util.Relation
import com.dbad.justintime.ui.theme.JustInTimeTheme

//Stateful
@Composable
fun ExtraRegistrationDetails(
    viewModel: UserDetailsViewModel,
    onCancelUserDetails: () -> Unit,
    onRegister: () -> Unit,
    userUid: Int,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    val event = viewModel::onEvent
    event(UserDetailsEvents.SetCancelEvent(onCancelUserDetails))
    event(UserDetailsEvents.SetRegisterEvent(onRegister))
    event(UserDetailsEvents.SetUserUid(userUid))

    ExtraRegistrationDetails(state = state, onEvent = event, modifier = modifier)
}

// Stateless
@Composable
fun ExtraRegistrationDetails(
    state: UserDetailsState,
    onEvent: (UserDetailsEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.TopStart, modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                JustInTimeLogoDisplay()
            }

            // User Text Input Area
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    //Name Field
                    TextInputField(
                        currentValue = state.name,
                        placeHolderText = stringResource(R.string.name),
                        onValueChange = { onEvent(UserDetailsEvents.SetName(it)) },
                        textFieldError = state.showNameFieldError,
                        errorString = stringResource(R.string.noNameProvided),
                        testingTag = TestTagNameField
                    )

                    // Pref Name Field
                    TextInputField(
                        currentValue = state.preferredName,
                        placeHolderText = stringResource(R.string.preferredName),
                        onValueChange = { onEvent(UserDetailsEvents.SetPrefName(name = it)) }
                    )

                    // DOB Picker
                    DateSelectorField(
                        currentValue = state.userDateOfBirth,
                        placeHolderText = stringResource(R.string.dateOfBirth),
                        showDatePicker = state.showDatePicker,
                        toggleDatePicker = { onEvent(UserDetailsEvents.ToggleDatePicker) },
                        dateError = state.showDatePickerError,
                        saveSelectedDate = { onEvent(UserDetailsEvents.SetDateOfBirth(it)) }
                    )

                    // Phone Number Field
                    TextInputField(
                        currentValue = state.phoneNumber,
                        placeHolderText = stringResource(R.string.phoneNumb),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        onValueChange = { onEvent(UserDetailsEvents.SetPhoneNumb(phone = it)) },
                        textFieldError = state.showPhoneNumbFieldError,
                        errorString = stringResource(R.string.invalidPhoneNumb),
                        testingTag = TestTagPhoneNumberField
                    )

                    // Pref Contact Method Drop Down
                    PreferredContactField(
                        currentValue = if (state.prefContactMethod == PreferredContactMethod.NONE) {
                            ""
                        } else {
                            stringResource(state.prefContactMethod.stringVal)
                        },
                        expandDropDown = state.prefContDropDownExpanded,
                        dropDownToggle = { onEvent(UserDetailsEvents.TogglePrefContactDropDown) },
                        selectContactMethod = { onEvent(UserDetailsEvents.SetPrefContactMethod(it)) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Emergency Contact Area
                    EmergencyContactDetails(state = state, onEvent = onEvent)

                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            DualButtonFields(
                leftButtonValue = stringResource(R.string.cancel),
                leftButtonOnClick = { onEvent(UserDetailsEvents.CancelEvent) },
                rightButtonValue = stringResource(R.string.register),
                rightButtonOnClick = { onEvent(UserDetailsEvents.RegisterEvent) }
            )
        }
    }
}

@Composable
fun EmergencyContactDetails(
    state: UserDetailsState,
    onEvent: (UserDetailsEvents) -> Unit
) {
    EmergencyContactField(
        isExpanded = state.emergencyContactAreaExpanded,
        expandableButtonClick = { onEvent(UserDetailsEvents.ToggleEmergencyContactArea) }
    ) {
        // Emergency Contact Name Field
        TextInputField(
            currentValue = state.emergencyContactName,
            placeHolderText = stringResource(R.string.name),
            onValueChange = { onEvent(UserDetailsEvents.SetEmergencyContactName(it)) },
            textFieldError = state.showEmergencyContactNameFieldError,
            errorString = stringResource(R.string.noNameProvided),
            testingTag = TestTagNameField
        )

        // Emergency Contact Pref Name Field
        TextInputField(
            currentValue = state.emergencyContactPrefName,
            placeHolderText = stringResource(R.string.preferredName),
            onValueChange = {
                onEvent(UserDetailsEvents.SetEmergencyContactPrefName(it))
            }
        )

        // Emergency Contact Phone Number Field
        TextInputField(
            currentValue = state.emergencyContactPhone,
            placeHolderText = stringResource(R.string.phoneNumb),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textFieldError = state.showEmergencyContactPhoneError,
            errorString = stringResource(R.string.invalidPhoneNumb),
            onValueChange = {
                onEvent(UserDetailsEvents.SetEmergencyContactPhoneNumb(it))
            },
            testingTag = TestTagPhoneNumberField
        )

        // Emergency Contact Email Field
        TextInputField(
            currentValue = state.emergencyContactEmail,
            placeHolderText = stringResource(R.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textFieldError = state.showEmergencyContactEmailError,
            errorString = stringResource(R.string.invalidEmailError),
            onValueChange = {
                onEvent(UserDetailsEvents.SetEmergencyContactEmail(it))
            },
            testingTag = TestTagEmailField
        )

        // Emergency Contact Pref Contact Drop Down
        PreferredContactField(
            currentValue = if (state.emergencyContactPrefContactMethod == PreferredContactMethod.NONE) {
                ""
            } else {
                stringResource(state.emergencyContactPrefContactMethod.stringVal)
            },
            expandDropDown = state.emergencyContactPrefContDropDownExpand,
            dropDownToggle = { onEvent(UserDetailsEvents.ToggleEmergencyContactPrefContactDropDown) },
            selectContactMethod = {
                onEvent(UserDetailsEvents.SetEmergencyContactPrefContactMethod(contactMethod = it))
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Emergency Contact Relation Drop Down
        RelationField(
            currentValue = if (state.emergencyContactRelation == Relation.NONE) {
                ""
            } else {
                stringResource(state.emergencyContactRelation.stringVal)
            },
            expandDropDown = state.emergencyContactRelationDropDownExpand,
            dropDownToggle = { onEvent(UserDetailsEvents.ToggleEmergencyContactRelationDropDown) },
            selectRelation = { onEvent(UserDetailsEvents.SetEmergencyContactRelation(relation = it)) }
        )
    }
}

@ViewingSystemThemes
@Composable
fun ExtraRegisterScreenPreview() {
    JustInTimeTheme { ExtraRegistrationDetails(state = UserDetailsState(), onEvent = {}) }
}

@ViewingSystemThemes
@Composable
fun ExtraRegisterScreenExtendedPreview() {
    JustInTimeTheme {
        ExtraRegistrationDetails(
            state = UserDetailsState(emergencyContactAreaExpanded = true),
            onEvent = {})
    }
}

@ViewingSystemThemes
@Composable
fun EmergencyContactDetailsPreview() {
    EmergencyContactDetails(
        state = UserDetailsState(emergencyContactAreaExpanded = true),
        onEvent = {})
}