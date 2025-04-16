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
import com.dbad.justintime.f_login_register.presentation.login.LoginScreen
import com.dbad.justintime.f_login_register.presentation.login.LoginViewModel
import com.dbad.justintime.f_login_register.presentation.register.RegisterScreen
import com.dbad.justintime.f_login_register.presentation.register.RegisterViewModel
import com.dbad.justintime.f_login_register.presentation.user_details.ExtraRegistrationDetails
import com.dbad.justintime.f_login_register.presentation.user_details.UserDetailsViewModel
import com.dbad.justintime.f_profile.presentation.profile.ProfileScreen
import com.dbad.justintime.f_profile.presentation.profile.ProfileViewModel
import com.dbad.justintime.f_shifts.presentation.ShiftsViewModel
import com.dbad.justintime.f_shifts.presentation.shifts_list.ShiftListScreen
import com.dbad.justintime.f_user_auth.data.data_source.UserAuthConnection
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo
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

                    val authenticated: AuthRepo = UserAuthConnection()
//                    authenticated.signOut()
                    val startingPosition =
                        if (authenticated.authState.value!!) ShiftNav else LoginNav

                    NavHost(navController = navController, startDestination = startingPosition) {

                        navigation<LoginNav>(startDestination = LoginScreen) {
                            val loginRegisterUseCases = App.loginRegister.useCases

                            composable<LoginScreen> {
                                LoginScreen(
                                    viewModel = viewModel<LoginViewModel>(
                                        factory = LoginViewModel.generateViewModel(
                                            useCases = loginRegisterUseCases,
                                            authUser = authenticated
                                        )
                                    ),
                                    onRegistration = {
                                        navController.navigate(route = RegistrationNav)
                                    },
                                    onLogin = {
                                        navController.navigate(route = ProfileNav)
                                    },
                                    modifier = Modifier.padding(paddingValues = innerPadding)
                                )
                            }

                            navigation<RegistrationNav>(startDestination = RegisterScreen) {
                                composable<RegisterScreen> {
                                    RegisterScreen(
                                        viewModel = viewModel<RegisterViewModel>(
                                            factory = RegisterViewModel.generateViewModel(
                                                useCases = loginRegisterUseCases,
                                                authUser = authenticated
                                            )
                                        ),
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
                                        viewModel = viewModel<UserDetailsViewModel>(
                                            factory = UserDetailsViewModel.generateViewModel(
                                                useCases = loginRegisterUseCases
                                            )
                                        ),
                                        onCancelUserDetails = {
                                            navController.navigate(route = LoginNav)
                                        },
                                        onRegister = {
                                            navController.navigate(route = ProfileNav)
                                        },
                                        userUid = args.userUid,
                                        modifier = Modifier.padding(
                                            paddingValues = innerPadding
                                        )
                                    )
                                }
                            }
                        }

                        navigation<ProfileNav>(startDestination = ProfileScreen) {
                            composable<ProfileScreen> {
                                ProfileScreen(
                                    viewModel = viewModel<ProfileViewModel>(
                                        factory = ProfileViewModel.generateViewModel(
                                            useCases = App.profile.useCases,
                                            authUser = authenticated
                                        )
                                    ),
                                    onSignOut = { navController.navigate(route = LoginNav) },
                                    onNavShiftView = { navController.navigate(route = ShiftNav) }
                                )
                            }
                        }

                        navigation<ShiftNav>(startDestination = ShiftScreen) {
                            composable<ShiftScreen> {
                                ShiftListScreen(
                                    viewModel = viewModel<ShiftsViewModel>(
                                        factory = ShiftsViewModel.generateViewModel()
                                    ),
                                    onNavProfile = { navController.navigate(route = ProfileNav) }
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
object LoginNav

@Serializable
object RegistrationNav

@Serializable
object LoginScreen

@Serializable
object RegisterScreen

@Serializable
data class UserDetailsInformation(val userUid: String)

@Serializable
object ProfileNav

@Serializable
object ProfileScreen

@Serializable
object ShiftNav

@Serializable
object ShiftScreen