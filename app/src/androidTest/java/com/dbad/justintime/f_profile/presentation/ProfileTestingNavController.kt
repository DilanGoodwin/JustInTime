package com.dbad.justintime.f_profile.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dbad.justintime.core.ProfileScreen
import com.dbad.justintime.f_local_datastore.domain.repository.UserPreferencesRepository
import com.dbad.justintime.f_login_register.data.UserPreferencesTestingImplementation
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import com.dbad.justintime.f_profile.presentation.profile.ProfileScreen
import com.dbad.justintime.f_profile.presentation.profile.ProfileViewModel

@Composable
fun ProfileTestingNavController(
    useCases: ProfileUseCases,
    userPreferencesStore: UserPreferencesRepository = UserPreferencesTestingImplementation()
) {
    val navControl = rememberNavController()

    NavHost(navController = navControl, startDestination = ProfileScreen) {
        composable<ProfileScreen> {
            ProfileScreen(
                viewModel = ProfileViewModel(
                    useCases = useCases,
                    preferencesDataStore = userPreferencesStore
                )
            )
        }
    }
}