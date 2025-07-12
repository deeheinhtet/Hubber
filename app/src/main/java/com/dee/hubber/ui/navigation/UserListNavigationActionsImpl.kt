package com.dee.hubber.ui.navigation

import androidx.navigation.NavController
import com.dee.hubber.user.presentation.userlist.navigation.UserListNavigationActions

class UserListNavigationActionsImpl(
    private val navController: NavController,
    private val appGraphNavController: NavController
) :
    UserListNavigationActions {
    override fun onNavigateUserDetails(username: String) {
        appGraphNavController.navigate(AppScreen.UserDetails.createRoute(username))
    }
}