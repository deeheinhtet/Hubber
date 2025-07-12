package com.dee.hubber.user.presentation.userlist

import com.dee.core_ui.base.AppEvent

sealed class UserListEvents : AppEvent {
    data class OnUserListLoadFailed(val errorMessage : String) : UserListEvents()
    data class OnNavigateToUserDetails(val name:String) : UserListEvents()
}