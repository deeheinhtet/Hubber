package com.dee.hubber.ui.navigation

import androidx.navigation.NavController
import com.dee.authenication.domain.navigation.AuthenticationNavigationActions

class AuthenticationNavigationActionsImpl(
    private val navController: NavController
) : AuthenticationNavigationActions {
    override fun navigateToDashboard() {
        navController.navigate(AppScreen.Dashboard.route) {
            popUpTo(AppScreen.Login.route) { inclusive = true }
        }
    }
}