package com.dee.hubber.user.presentation.userdetails.navigation

import android.content.Context

interface UserDetailsNavigationActions {
    fun onPopBackFromDetails()
    fun onViewUserRepository(url: String,context: Context)
    fun onViewGithubUser(url: String,context: Context)
}