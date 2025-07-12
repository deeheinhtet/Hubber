package com.dee.authenication.presentation.login

import android.net.Uri
import com.dee.core_ui.base.AppEvent

sealed class LoginEvents : AppEvent {
    data class OnOAuthStart(val uri: Uri) : LoginEvents()
    data class OnOAuthLoginFailed(val message: String) : LoginEvents()
    data object OnOAuthLoginSuccess : LoginEvents()
}