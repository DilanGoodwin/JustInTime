package com.dbad.justintime.f_profile.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.DateSelectorField
import com.dbad.justintime.core.presentation.util.ExpandableCardArea
import com.dbad.justintime.core.presentation.util.PasswordField
import com.dbad.justintime.core.presentation.util.PreferredContactField
import com.dbad.justintime.core.presentation.util.RelationField
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPasswordChangeExpandableField
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.core.presentation.util.TestTagPasswordMatchField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TextInputField
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = { ProfileTopBar() },
        floatingActionButton = {
            if (false) { //TODO If content edited show action button else hide it
                SmallFloatingActionButton(onClick = {}) {
                    Box(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "Save",
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
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {

                // Name Field
                TextInputField(
                    currentValue = "",
                    placeHolderText = stringResource(R.string.name),
                    textFieldError = false,
                    errorString = "",
                    onValueChange = {},
                    testingTag = TestTagNameField
                )

                // Pref Name Field
                TextInputField(
                    currentValue = "",
                    placeHolderText = stringResource(R.string.preferredName),
                    onValueChange = {}
                )

                // Email Field
                TextInputField(
                    currentValue = "",
                    placeHolderText = stringResource(R.string.email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = {},
                    textFieldError = false,
                    errorString = stringResource(R.string.invalidEmailError),
                    testingTag = TestTagEmailField
                )

                // Password Change Field
                PasswordUpdateFields()
                Spacer(modifier = Modifier.height(20.dp))

                // DOB Picker
                DateSelectorField(
                    currentValue = "",
                    placeHolderText = stringResource(R.string.dateOfBirth),
                    showDatePicker = false,
                    toggleDatePicker = {},
                    dateError = false,
                    saveSelectedDate = {}
                )

                // Phone Number Field
                TextInputField(
                    currentValue = "",
                    placeHolderText = stringResource(R.string.phoneNumb),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = {},
                    textFieldError = false,
                    errorString = stringResource(R.string.invalidPhoneNumb),
                    testingTag = TestTagPhoneNumberField
                )

                // Pref Contact Method Drop Down
                PreferredContactField(
                    currentValue = "",
                    expandDropDown = false,
                    dropDownToggle = {},
                    selectContactMethod = {}
                )

                // Emergency Contact Area
                EmergencyContactArea()
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(R.string.profile)) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordUpdateFields() {
    ExpandableCardArea(
        isExpanded = true,
        expandableButtonClick = {},
        cardTitle = stringResource(R.string.passwordChangeFields),
        testTag = TestTagPasswordChangeExpandableField
    ) {
        PasswordField(
            currentValue = "",
            placeHolderText = stringResource(R.string.oldPassword),
            showPassword = false,
            textFieldError = false,
            errorString = "",
            onValueChange = {},
            visiblePassword = {},
            testingTag = TestTagPasswordField
        )

        PasswordField(
            currentValue = "",
            placeHolderText = stringResource(R.string.newPassword),
            showPassword = false,
            textFieldError = false,
            errorString = "",
            onValueChange = {},
            visiblePassword = {},
            testingTag = TestTagPasswordField
        )

        PasswordField(
            currentValue = "",
            placeHolderText = stringResource(R.string.reEnterNewPassword),
            showPassword = false,
            textFieldError = false,
            errorString = "",
            onValueChange = {},
            visiblePassword = {},
            testingTag = TestTagPasswordMatchField
        )
    }
}

@Composable
fun EmergencyContactArea() {
    ExpandableCardArea(
        isExpanded = true,
        expandableButtonClick = {},
        cardTitle = stringResource(R.string.emergencyContact),
        testTag = TestTagEmergencyContactExpandableField
    ) {
        // Emergency Contact Name Field
        TextInputField(
            currentValue = "",
            placeHolderText = stringResource(R.string.name),
            onValueChange = {},
            textFieldError = false,
            errorString = stringResource(R.string.noNameProvided),
            testingTag = TestTagNameField
        )

        // Emergency Contact Pref Name Field
        TextInputField(
            currentValue = "",
            placeHolderText = stringResource(R.string.preferredName),
            onValueChange = {}
        )

        // Emergency Contact Phone Number Field
        TextInputField(
            currentValue = "",
            placeHolderText = stringResource(R.string.phoneNumb),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textFieldError = false,
            errorString = stringResource(R.string.invalidPhoneNumb),
            onValueChange = {},
            testingTag = TestTagPhoneNumberField
        )

        // Emergency Contact Email Field
        TextInputField(
            currentValue = "",
            placeHolderText = stringResource(R.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textFieldError = false,
            errorString = stringResource(R.string.invalidEmailError),
            onValueChange = {},
            testingTag = TestTagEmailField
        )

        // Emergency Contact Pref Contact Drop Down
        PreferredContactField(
            currentValue = "",
            expandDropDown = false,
            dropDownToggle = {},
            selectContactMethod = {}
        )

        Spacer(modifier = Modifier.height(15.dp))

        // Emergency Contact Relation Drop Down
        RelationField(
            currentValue = "",
            expandDropDown = false,
            dropDownToggle = {},
            selectRelation = {}
        )
    }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenPreview() {
    JustInTimeTheme { ProfileScreen() }
}

@ViewingSystemThemes
@Composable //TODO Expand emergency contact area
fun ProfileScreenEmergencyContactPreview() {
    JustInTimeTheme { EmergencyContactArea() }
}

@ViewingSystemThemes
@Composable //TODO Expand Password Area
fun ProfileScreenPasswordChangePreview() {
    JustInTimeTheme { PasswordUpdateFields() }
}