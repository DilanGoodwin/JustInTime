package com.dbad.justintime.f_login_register.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dbad.justintime.f_login_register.domain.use_case.UserUseCases
import com.dbad.justintime.f_login_register.presentation.register.RegisterScreen
import com.dbad.justintime.f_login_register.presentation.register.RegisterViewModel
import kotlinx.serialization.Serializable

@Composable
fun LoginRegisterTestingNavController(useCases: UserUseCases) {
    val navControl = rememberNavController()

    NavHost(navController = navControl, startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreen(
                viewModel = LoginViewModel(useCases = useCases),
                onRegistration = {
                    navControl.navigate(route = RegisterScreen(it))
                }
            )
        }
        composable<RegisterScreen> {
            RegisterScreen(viewModel = RegisterViewModel())
        }
    }
}

@Serializable
object LoginScreen

@Serializable
data class RegisterScreen(val email: String?)