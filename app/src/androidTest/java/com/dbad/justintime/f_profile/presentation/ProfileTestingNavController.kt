package com.dbad.justintime.f_profile.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dbad.justintime.core.ProfileScreen
import com.dbad.justintime.f_profile.domain.use_case.ProfileUseCases
import com.dbad.justintime.f_profile.presentation.profile.ProfileScreen
import com.dbad.justintime.f_profile.presentation.profile.ProfileViewModel
import com.dbad.justintime.f_user_auth.domain.repository.AuthRepo

@Composable
fun ProfileTestingNavController(
    useCases: ProfileUseCases,
    authUser: AuthRepo
) {
    val navControl = rememberNavController()

    NavHost(navController = navControl, startDestination = ProfileScreen) {
        composable<ProfileScreen> {
            ProfileScreen(
                viewModel = ProfileViewModel(
                    useCases = useCases,
                    authUser = authUser
                )
            )
        }
    }
}