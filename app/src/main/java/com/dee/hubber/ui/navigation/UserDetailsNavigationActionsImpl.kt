package com.dee.hubber.ui.navigation

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.dee.hubber.user.presentation.userdetails.navigation.UserDetailsNavigationActions

class UserDetailsNavigationActionsImpl(
    private val navController: NavController
) :
    UserDetailsNavigationActions {

    override fun onPopBackFromDetails() {
        navController.popBackStack()
    }

    override fun onViewUserRepository(url: String, context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open URL", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewGithubUser(url: String, context: Context) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open URL", Toast.LENGTH_SHORT).show()
        }
    }
}