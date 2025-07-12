package com.dee.hubber.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed class AppScreen(val route: String) {
    @Serializable
    data object Login : AppScreen("login")

    @Serializable
    data object Dashboard : AppScreen("dashboard")

    @Serializable
    data object UserDetails : AppScreen("user_details/{username}"){
        fun createRoute(username: String) = "user_details/$username"
    }

    data object UserList : AppScreen("dashboard/users")
    data object Settings : AppScreen("dashboard/settings")
}

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector = icon
) {
    data object Users : BottomNavItem(
        route = AppScreen.UserList.route,
        title = "Users",
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person
    )

    data object Settings : BottomNavItem(
        route = AppScreen.Settings.route,
        title = "Settings",
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )
}
