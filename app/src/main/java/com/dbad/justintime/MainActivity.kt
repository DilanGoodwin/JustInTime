package com.dbad.justintime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dbad.justintime.f_local_datastore.domain.model.UserPreferencesRepositoryImplementation
import com.dbad.justintime.f_login_register.presentation.login.LoginScreen
import com.dbad.justintime.f_login_register.presentation.login.LoginViewModel
import com.dbad.justintime.f_login_register.presentation.register.RegisterScreen
import com.dbad.justintime.f_login_register.presentation.register.RegisterViewModel
import com.dbad.justintime.f_login_register.presentation.user_details.ExtraRegistrationDetails
import com.dbad.justintime.f_login_register.presentation.user_details.UserDetailsViewModel
import com.dbad.justintime.f_profile.presentation.profile.ProfileScreen
import com.dbad.justintime.f_profile.presentation.profile.ProfileViewModel
import com.dbad.justintime.ui.theme.JustInTimeTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JustInTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    val loginRegisterUseCases = App.loginRegister.useCases

                    val userPreferences = UserPreferencesRepositoryImplementation(this)
                    val storedLoginState = userPreferences.tokenFlow
                    val startingPosition =
                        runBlocking { if (storedLoginState.first() != "") ProfileScreen else LoginNav }

                    NavHost(navController = navController, startDestination = startingPosition) {

                        navigation<LoginNav>(startDestination = LoginScreen) {

                            composable<LoginScreen> {
                                LoginScreen(
                                    viewModel = viewModel<LoginViewModel>(
                                        factory = LoginViewModel.generateViewModel(
                                            useCases = loginRegisterUseCases,
                                            preferencesDataStore = userPreferences
                                        )
                                    ),
                                    onRegistration = {
                                        navController.navigate(route = RegistrationNav)
                                    },
                                    onLogin = { navController.navigate(route = ProfileScreen) },
                                    modifier = Modifier.padding(paddingValues = innerPadding)
                                )
                            }

                            navigation<RegistrationNav>(startDestination = RegisterScreen) {
                                composable<RegisterScreen> {
                                    RegisterScreen(
                                        viewModel = RegisterViewModel(useCases = loginRegisterUseCases),
                                        onCancelRegistration = {
                                            navController.navigate(route = LoginScreen)
                                        },
                                        onRegistration = {
                                            navController.navigate(
                                                route = UserDetailsInformation(it)
                                            )
                                        },
                                        modifier = Modifier.padding(paddingValues = innerPadding)
                                    )
                                }

                                composable<UserDetailsInformation> {
                                    val args = it.toRoute<UserDetailsInformation>()
                                    ExtraRegistrationDetails(
                                        viewModel = UserDetailsViewModel(
                                            useCases = loginRegisterUseCases,
                                            preferencesDataStore = userPreferences
                                        ),
                                        onCancelUserDetails = {
                                            navController.navigate(route = LoginNav)
                                        },
                                        onRegister = {
                                            navController.navigate(route = ProfileScreen)
                                        },
                                        userUid = args.userUid,
                                        modifier = Modifier.padding(
                                            paddingValues = innerPadding
                                        )
                                    )
                                }
                            }
                        }

                        composable<ProfileScreen> { //TODO move to separate nav window
                            ProfileScreen(
                                viewModel = ProfileViewModel(),
                                userId = ""
                            )
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
object MainApplication

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
data class UserDetailsInformation(val userUid: String)

@Serializable
object ProfileScreen