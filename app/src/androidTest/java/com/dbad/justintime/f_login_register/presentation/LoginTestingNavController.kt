package com.dbad.justintime.f_login_register.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dbad.justintime.core.LoginScreenRoute
import com.dbad.justintime.core.ProfileScreen
import com.dbad.justintime.core.RegisterScreenRoute
import com.dbad.justintime.core.UserDetailsRoute
import com.dbad.justintime.core.domain.use_case.ValidateDate
import com.dbad.justintime.core.domain.use_case.ValidateEmail
import com.dbad.justintime.core.domain.use_case.ValidatePassword
import com.dbad.justintime.core.domain.use_case.ValidatePhoneNumber
import com.dbad.justintime.f_login_register.data.ProfileRepositoryTestingImplementation
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.login.LoginScreen
import com.dbad.justintime.f_login_register.presentation.login.LoginViewModel
import com.dbad.justintime.f_login_register.presentation.register.RegisterScreen
import com.dbad.justintime.f_login_register.presentation.register.RegisterViewModel
import com.dbad.justintime.f_login_register.presentation.user_details.ExtraRegistrationDetails
import com.dbad.justintime.f_login_register.presentation.user_details.UserDetailsEvents
import com.dbad.justintime.f_login_register.presentation.user_details.UserDetailsViewModel
import com.dbad.justintime.f_profile.domain.use_case.GetEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.GetEmployee
import com.dbad.justintime.f_profile.domain.use_case.GetUser
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmergencyContact
import com.dbad.justintime.f_profile.domain.use_case.UpsertEmployee
import com.dbad.justintime.f_profile.domain.use_case.UpsertUser
import com.dbad.justintime.f_profile.presentation.profile.ProfileScreen
import com.dbad.justintime.f_profile.presentation.profile.ProfileViewModel
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo

@Composable
fun LoginTestingNavController(
    authUser: AuthRepo,
    useCases: UserUseCases,
    dateOfBirth: String = ""
) {
    val navControl = rememberNavController()

    NavHost(navController = navControl, startDestination = LoginScreenRoute) {
        composable<LoginScreenRoute> {
            LoginScreen(
                viewModel = LoginViewModel(
                    useCases = useCases,
                    authUser = authUser
                ),
                onRegistration = {
                    navControl.navigate(route = RegisterScreenRoute)
                },
                onLogin = { navControl.navigate(route = ProfileScreen) }
            )
        }
        composable<RegisterScreenRoute> {
            val registerViewModel = RegisterViewModel(
                useCases = useCases,
                authUser = authUser
            )
            RegisterScreen(
                viewModel = registerViewModel,
                onCancelRegistration = { navControl.navigate(route = LoginScreenRoute) },
                onRegistration = {
                    navControl.navigate(route = UserDetailsRoute(it))
                }
            )
        }
        composable<UserDetailsRoute> {
            val args = it.toRoute<UserDetailsRoute>()
            val viewModel = UserDetailsViewModel(
                useCases = useCases
            )

            viewModel.onEvent(UserDetailsEvents.SetDateOfBirth(dateOfBirth = dateOfBirth))
            viewModel.onEvent(UserDetailsEvents.ToggleDatePicker)

            ExtraRegistrationDetails(
                viewModel = viewModel,
                onCancelUserDetails = { navControl.navigate(route = LoginScreenRoute) },
                onRegister = { navControl.navigate(route = ProfileScreen) },
                userUid = args.userUid
            )
        }

        composable<ProfileScreen> {
            // Create blank instance of ProfileUseCases
            val profileRepo = ProfileRepositoryTestingImplementation()
            val profileUseCases = ProfileUseCases(
                getUser = GetUser(repository = profileRepo),
                upsertUser = UpsertUser(repository = profileRepo),
                getEmployee = GetEmployee(repository = profileRepo),
                upsertEmployee = UpsertEmployee(repository = profileRepo),
                getEmergencyContact = GetEmergencyContact(repository = profileRepo),
                upsertEmergencyContact = UpsertEmergencyContact(repository = profileRepo),
                validateEmail = ValidateEmail(),
                validatePassword = ValidatePassword(),
                validatePhoneNumber = ValidatePhoneNumber(),
                validateDate = ValidateDate()
            )

            ProfileScreen(
                viewModel = ProfileViewModel(
                    useCases = profileUseCases,
                    authUser = authUser
                ),
                onSignOut = {},
                onNavShiftView = {}
            )
        }
    }
}