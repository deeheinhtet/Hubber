package com.dee.hubber.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dee.authenication.presentation.login.LoginScreen
import com.dee.core_ui.base.BaseEvent
import com.dee.hubber.ui.dashboard.DashboardScreen
import com.dee.hubber.ui.navigation.AppScreen
import com.dee.hubber.ui.navigation.AuthenticationNavigationActionsImpl
import com.dee.hubber.ui.navigation.UserDetailsNavigationActionsImpl
import com.dee.hubber.user.presentation.userdetails.UserDetailsScreen
import com.dee.hubber.utils.AuthEventManager


@Composable
fun AppGraph(
    modifier: Modifier = Modifier,
    initialScreen: AppScreen = AppScreen.Login,
    authEventManager: AuthEventManager
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        authEventManager.event.collect { event ->
            when (event) {
                is BaseEvent.TokenExpired -> {
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = initialScreen.route,
        modifier = modifier
    ) {
        composable(route = AppScreen.Login.route) {
            val navigationActions = remember {
                AuthenticationNavigationActionsImpl(navController)
            }
            LoginScreen(
                navigationActions = navigationActions,
            )
        }
        composable(route = AppScreen.Dashboard.route) {
            DashboardScreen(
                authEventManager,
                navController
            )
        }

        composable(
            route = AppScreen.UserDetails.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            val navigationActions = remember {
                UserDetailsNavigationActionsImpl(navController)
            }
            UserDetailsScreen(
                navigationActions,
                username = username.orEmpty(),

            )
        }
    }
}

