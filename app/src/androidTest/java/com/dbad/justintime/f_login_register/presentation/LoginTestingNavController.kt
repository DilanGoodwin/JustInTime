package com.dbad.justintime.f_login_register.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dbad.justintime.f_login_register.core.LoginScreenRoute
import com.dbad.justintime.f_login_register.core.ProfileScreen
import com.dbad.justintime.f_login_register.core.RegisterScreenRoute
import com.dbad.justintime.f_login_register.core.UserDetailsRoute
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.login.LoginScreen
import com.dbad.justintime.f_login_register.presentation.login.LoginViewModel
import com.dbad.justintime.f_login_register.presentation.register.RegisterScreen
import com.dbad.justintime.f_login_register.presentation.register.RegisterViewModel
import com.dbad.justintime.f_login_register.presentation.user_details.ExtraRegistrationDetails
import com.dbad.justintime.f_login_register.presentation.user_details.UserDetailsViewModel
import com.dbad.justintime.f_profile.presentation.profile.ProfileScreen

@Composable
fun LoginTestingNavController(useCases: UserUseCases) {
    val navControl = rememberNavController()

    NavHost(navController = navControl, startDestination = LoginScreenRoute) {
        composable<LoginScreenRoute> {
            LoginScreen(
                viewModel = LoginViewModel(useCases = useCases),
                onRegistration = {
                    navControl.navigate(route = RegisterScreenRoute)
                }
            )
        }
        composable<RegisterScreenRoute> {
            val registerViewModel = RegisterViewModel(useCases = useCases)
            RegisterScreen(
                viewModel = registerViewModel,
                onCancelRegistration = { navControl.navigate(route = LoginScreenRoute) },
                onRegistration = {
                    navControl.navigate(
                        route = UserDetailsRoute(
                            email = registerViewModel.state.value.email,
                            password = registerViewModel.state.value.password
                        )
                    )
                }
            )
        }
        composable<UserDetailsRoute> {
            val args = it.toRoute<UserDetailsRoute>()
            ExtraRegistrationDetails(
                viewModel = UserDetailsViewModel(useCases = useCases),
                onCancelUserDetails = { navControl.navigate(route = LoginScreenRoute) },
                onRegister = { navControl.navigate(route = ProfileScreen) },
                email = args.email,
                password = args.password
            )
        }

        composable<ProfileScreen> {
            ProfileScreen()
        }
    }
}