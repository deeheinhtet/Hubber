package com.dee.authenication.presentation.login

import com.dee.authenication.domain.model.AuthState

data class LoginUiState(
    val authState: AuthState = AuthState.UNAUTHORIZED_USER,
    val userInputClientToken: String? = null,
)