package com.dbad.justintime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.dbad.justintime.f_login_register.presentation.login.LoginScreen
import com.dbad.justintime.f_login_register.presentation.login.LoginViewModel
import com.dbad.justintime.f_login_register.presentation.register.RegisterScreen
import com.dbad.justintime.f_login_register.presentation.register.RegisterViewModel
import com.dbad.justintime.f_login_register.presentation.user_details.ExtraRegistrationDetails
import com.dbad.justintime.ui.theme.JustInTimeTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustInTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val useCases = App.loginRegister.useCases
                    NavHost(navController = navController, startDestination = LoginNav) {

                        navigation<LoginNav>(startDestination = LoginScreen) {

                            composable<LoginScreen> {
                                LoginScreen(
                                    viewModel = LoginViewModel(useCases = useCases),
                                    onRegistration = {
                                        navController.navigate(route = RegistrationNav)
                                    },
                                    modifier = Modifier.padding(paddingValues = innerPadding)
                                )
                            }

                            navigation<RegistrationNav>(startDestination = RegisterScreen) {
                                val sharedViewModel = RegisterViewModel(useCases = useCases)

                                composable<RegisterScreen> {
                                    RegisterScreen(
                                        viewModel = sharedViewModel,
                                        onCancelRegistration = {
                                            navController.navigate(LoginScreen)
                                        },
                                        onRegistration = {
                                            navController.navigate(UserDetailsInformation)
                                        },
                                        modifier = Modifier.padding(paddingValues = innerPadding)
                                    )
                                }

                                composable<UserDetailsInformation> {
                                    ExtraRegistrationDetails()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


// Navigation Serialization objects
@Serializable
object LoginNav

@Serializable
object RegistrationNav

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
object UserDetailsInformation