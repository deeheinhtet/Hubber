package com.dee.hubber.user.presentation.userlist

import com.dee.hubber.user.domain.model.GitHubUser

data class UserListUiState(
    val users: List<GitHubUser> = emptyList(),
    val hasError: String? = null,
    val pagination: UsersPaginationState = UsersPaginationState(),
)

data class UsersPaginationState(
    val since: Long? = null,
    val hasMore: Boolean = true,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val totalLoaded: Int = 0,
    val searchPage : Int = 1
)