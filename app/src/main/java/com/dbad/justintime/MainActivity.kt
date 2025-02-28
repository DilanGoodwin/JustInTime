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
                    NavHost(navController = navController, startDestination = TitleScreen) {
                        navigation<TitleScreen>(startDestination = LoginScreen) {
                            composable<LoginScreen> {
                                val loginViewModel = LoginViewModel(App.loginRegister.useCases)
                                LoginScreen(
                                    viewModel = loginViewModel,
                                    onRegistration = {
                                        navController.navigate(route = RegisterScreen(it))
                                    },
                                    modifier = Modifier.padding(paddingValues = innerPadding)
                                )
                            }
                            composable<RegisterScreen> {
                                RegisterScreen(
                                    viewModel = RegisterViewModel(),
                                    modifier = Modifier.padding(paddingValues = innerPadding)
                                )
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
object TitleScreen

@Serializable
object LoginScreen

@Serializable
data class RegisterScreen(val email: String?)