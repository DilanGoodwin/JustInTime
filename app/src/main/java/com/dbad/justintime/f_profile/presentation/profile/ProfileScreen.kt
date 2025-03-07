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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dbad.justintime.R
import com.dbad.justintime.core.presentation.util.DateSelectorField
import com.dbad.justintime.core.presentation.util.ExpandableCardArea
import com.dbad.justintime.core.presentation.util.LabelledTextInputFields
import com.dbad.justintime.core.presentation.util.PasswordField
import com.dbad.justintime.core.presentation.util.PreferredContactField
import com.dbad.justintime.core.presentation.util.RelationField
import com.dbad.justintime.core.presentation.util.TestTagCompanyInformationExpandableField
import com.dbad.justintime.core.presentation.util.TestTagEmailField
import com.dbad.justintime.core.presentation.util.TestTagEmergencyContactExpandableField
import com.dbad.justintime.core.presentation.util.TestTagNameField
import com.dbad.justintime.core.presentation.util.TestTagPasswordChangeExpandableField
import com.dbad.justintime.core.presentation.util.TestTagPasswordField
import com.dbad.justintime.core.presentation.util.TestTagPasswordMatchField
import com.dbad.justintime.core.presentation.util.TestTagPhoneNumberField
import com.dbad.justintime.core.presentation.util.TextInputField
import com.dbad.justintime.core.presentation.util.ViewingSystemThemes
import com.dbad.justintime.f_login_register.domain.model.util.PreferredContactMethod
import com.dbad.justintime.f_login_register.domain.model.util.Relation
import com.dbad.justintime.ui.theme.JustInTimeTheme

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val state by viewModel.state.collectAsState()

    ProfileScreen(state = state)
}

@Composable
fun ProfileScreen(state: ProfileState) {
    Scaffold(
        topBar = { ProfileTopBar() },
        floatingActionButton = {
            if (state.changeMade) { //TODO If content edited show action button else hide it
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
                    currentValue = state.employee.name,
                    placeHolderText = stringResource(R.string.name),
                    textFieldError = state.userNameError,
                    errorString = stringResource(R.string.noNameProvided),
                    onValueChange = {},
                    testingTag = TestTagNameField
                )

                // Pref Name Field
                TextInputField(
                    currentValue = state.employee.preferredName,
                    placeHolderText = stringResource(R.string.preferredName),
                    onValueChange = {}
                )

                // Email Field
                TextInputField(
                    currentValue = state.user.email,
                    placeHolderText = stringResource(R.string.email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = {},
                    textFieldError = state.userEmailError,
                    errorString = stringResource(R.string.invalidEmailError),
                    testingTag = TestTagEmailField
                )

                // DOB Picker
                DateSelectorField(
                    currentValue = state.employee.dateOfBirth,
                    placeHolderText = stringResource(R.string.dateOfBirth),
                    showDatePicker = state.showDateOfBirthPicker,
                    toggleDatePicker = {},
                    dateError = state.dateOfBirthError,
                    saveSelectedDate = {}
                )

                // Phone Number Field
                TextInputField(
                    currentValue = state.employee.dateOfBirth,
                    placeHolderText = stringResource(R.string.phoneNumb),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = {},
                    textFieldError = state.userPhoneError,
                    errorString = stringResource(R.string.invalidPhoneNumb),
                    testingTag = TestTagPhoneNumberField
                )

                // Pref Contact Method Drop Down
                PreferredContactField(
                    currentValue = if (state.employee.preferredContactMethod == PreferredContactMethod.NONE) {
                        ""
                    } else {
                        stringResource(state.employee.preferredContactMethod.stringVal)
                    },
                    expandDropDown = state.expandPrefContactMethod,
                    dropDownToggle = {},
                    selectContactMethod = {}
                )
                Spacer(modifier = Modifier.height(20.dp))

                // Emergency Contact Area
                EmergencyContactArea(state = state)
                Spacer(modifier = Modifier.height(20.dp))

                // Password Change Field
                PasswordUpdateFields(state = state)
                Spacer(modifier = Modifier.height(20.dp))

                // Company Information
                CompanyInformationArea(state = state)
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
fun PasswordUpdateFields(state: ProfileState) {
    ExpandableCardArea(
        isExpanded = state.expandPasswordArea,
        expandableButtonClick = {},
        cardTitle = stringResource(R.string.passwordChangeFields),
        testTag = TestTagPasswordChangeExpandableField
    ) {
        // Old Password
        PasswordField(
            currentValue = state.oldPassword,
            placeHolderText = stringResource(R.string.oldPassword),
            showPassword = state.oldPasswordView,
            textFieldError = state.oldPasswordShowError,
            errorString = stringResource(state.oldPasswordErrorString.errorCode),
            onValueChange = {},
            visiblePassword = {},
            testingTag = TestTagPasswordField
        )

        // New Password
        PasswordField(
            currentValue = state.newPassword,
            placeHolderText = stringResource(R.string.newPassword),
            showPassword = state.newPasswordView,
            textFieldError = state.newPasswordShowError,
            errorString = stringResource(state.newPasswordErrorString.errorCode),
            onValueChange = {},
            visiblePassword = {},
            testingTag = TestTagPasswordField
        )

        // New Password Match
        PasswordField(
            currentValue = state.newMatchPassword,
            placeHolderText = stringResource(R.string.reEnterNewPassword),
            showPassword = state.newMatchPasswordView,
            textFieldError = state.newMatchPasswordShowError,
            errorString = stringResource(R.string.passwordDoNotMatch),
            onValueChange = {},
            visiblePassword = {},
            testingTag = TestTagPasswordMatchField
        )
    }
}

@Composable
fun EmergencyContactArea(state: ProfileState) {
    ExpandableCardArea(
        isExpanded = state.expandEmergencyContactArea,
        expandableButtonClick = {},
        cardTitle = stringResource(R.string.emergencyContact),
        testTag = TestTagEmergencyContactExpandableField
    ) {
        // Emergency Contact Name Field
        TextInputField(
            currentValue = state.emergencyContact.name,
            placeHolderText = stringResource(R.string.name),
            onValueChange = {},
            textFieldError = state.emergencyContactNameError,
            errorString = stringResource(R.string.noNameProvided),
            testingTag = TestTagNameField
        )

        // Emergency Contact Pref Name Field
        TextInputField(
            currentValue = state.emergencyContact.preferredName,
            placeHolderText = stringResource(R.string.preferredName),
            onValueChange = {}
        )

        // Emergency Contact Phone Number Field
        TextInputField(
            currentValue = state.emergencyContact.phone,
            placeHolderText = stringResource(R.string.phoneNumb),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            textFieldError = state.emergencyContactPhoneError,
            errorString = stringResource(R.string.invalidPhoneNumb),
            onValueChange = {},
            testingTag = TestTagPhoneNumberField
        )

        // Emergency Contact Email Field
        TextInputField(
            currentValue = state.emergencyContact.email,
            placeHolderText = stringResource(R.string.email),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            textFieldError = state.emergencyContactEmailError,
            errorString = stringResource(R.string.invalidEmailError),
            onValueChange = {},
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
            dropDownToggle = {},
            selectContactMethod = {}
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
            dropDownToggle = {},
            selectRelation = {}
        )
    }
}

@Composable
fun CompanyInformationArea(state: ProfileState) {
    ExpandableCardArea(
        isExpanded = state.expandCompanyInformationArea,
        expandableButtonClick = {},
        cardTitle = stringResource(R.string.companyInformation),
        testTag = TestTagCompanyInformationExpandableField
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            LabelledTextInputFields(
                currentValue = state.employee.companyName,
                placeHolderText = stringResource(R.string.companyName),
                onValueChange = {},
                readOnly = !state.employee.isAdmin,
                testingTag = ""
            )

            LabelledTextInputFields(
                currentValue = state.companyInformationManagerName,
                placeHolderText = stringResource(R.string.directReport),
                onValueChange = {},
                readOnly = !state.employee.isAdmin,
                testingTag = ""
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                LabelledTextInputFields(
                    currentValue = stringResource(state.employee.contractType.stringVal),
                    placeHolderText = stringResource(R.string.contractType),
                    onValueChange = {},
                    readOnly = !state.employee.isAdmin,
                    modifier = Modifier.width(180.dp),
                    testingTag = ""
                )

                LabelledTextInputFields(
                    currentValue = state.employee.role,
                    placeHolderText = stringResource(R.string.role),
                    onValueChange = {},
                    readOnly = !state.employee.isAdmin,
                    modifier = Modifier.width(190.dp),
                    testingTag = ""
                )
            }
        }
    }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenPreview() {
    JustInTimeTheme { ProfileScreen(state = ProfileState()) }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenPasswordChangePreview() {
    JustInTimeTheme { PasswordUpdateFields(state = ProfileState(expandPasswordArea = true)) }
}

@ViewingSystemThemes
@Composable //TODO Expand emergency contact area
fun ProfileScreenEmergencyContactPreview() {
    JustInTimeTheme { EmergencyContactArea(state = ProfileState(expandEmergencyContactArea = true)) }
}

@ViewingSystemThemes
@Composable
fun ProfileScreenCompanyInformationPreview() {
    JustInTimeTheme { CompanyInformationArea(state = ProfileState(expandCompanyInformationArea = true)) }
}