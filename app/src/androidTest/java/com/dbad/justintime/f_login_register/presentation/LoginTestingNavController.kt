package com.dbad.justintime.f_login_register.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dbad.justintime.f_login_register.core.LoginScreenRoute
import com.dbad.justintime.f_login_register.core.RegisterScreenRoute
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.register.RegisterScreen
import com.dbad.justintime.f_login_register.presentation.register.RegisterViewModel

@Composable
fun LoginTestingNavController(useCases: UserUseCases) {
    val navControl = rememberNavController()

    NavHost(navController = navControl, startDestination = LoginScreenRoute) {
        composable<LoginScreenRoute> {
            LoginScreen(
                viewModel = LoginViewModel(useCases = useCases),
                onRegistration = {
                    navControl.navigate(route = RegisterScreenRoute(it))
                }
            )
        }
        composable<RegisterScreenRoute> {
            val args = it.toRoute<RegisterScreenRoute>()
            RegisterScreen(
                viewModel = RegisterViewModel(useCases = useCases),
                onCancelRegistration = { navControl.navigate(route = LoginScreenRoute) },
                passedEmailValue = args.email
            )
        }
    }
}