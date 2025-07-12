package com.dee.hubber.user.presentation.settings

import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.model.GithubUserRepository


data class SettingsUiState(
    val user : GitHubUser? = null,
    val error : String? = null,
    val repositories : List<GithubUserRepository> = emptyList()
)