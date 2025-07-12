package com.dee.hubber.ui.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dee.hubber.R
import com.dee.hubber.ui.navigation.AppScreen
import com.dee.hubber.ui.navigation.BottomNavItem
import com.dee.hubber.ui.navigation.SettingsNavigationActionsImpl
import com.dee.hubber.ui.navigation.UserListNavigationActionsImpl
import com.dee.hubber.user.presentation.settings.SettingsScreen
import com.dee.hubber.user.presentation.userlist.UserListScreen
import com.dee.hubber.utils.AuthEventManager

@Composable
fun DashboardScreen(
    authEventManager: AuthEventManager,
    appGraphNavController: NavController,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val bottomNavItems = listOf(
        BottomNavItem.Users,
        BottomNavItem.Settings
    )
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(),
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.route) {
                                    item.selectedIcon
                                } else {
                                    item.icon
                                },
                                contentDescription = item.title
                            )
                        },
                        label = {
                            val title = when (item) {
                                BottomNavItem.Users -> stringResource(R.string.label_user_menu)
                                BottomNavItem.Settings -> stringResource(R.string.label_settings_menu)
                            }
                            Text(
                                title, style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (item.route == currentRoute) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route)
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValue ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.UserList.route,
            modifier = Modifier.padding(paddingValue)
        ) {
            composable(route = AppScreen.UserList.route) {
                val userListNavigationActions =
                    remember { UserListNavigationActionsImpl(navController, appGraphNavController) }
                UserListScreen(userListNavigationActions)
            }

            composable(route = AppScreen.Settings.route) {
                val navigationActions = remember {
                    SettingsNavigationActionsImpl(navController, authEventManager)
                }
                SettingsScreen(
                    navigationActions
                )
            }
        }
    }
}