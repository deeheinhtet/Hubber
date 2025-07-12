package com.dee.hubber.user.presentation.userdetails

import com.dee.hubber.user.data.remote.dto.GitHubRepositoryDto
import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.model.GithubUserRepository


data class UserDetailsUiState(
    val user : GitHubUser? = null,
    val error : String? = null,
    val repositories: List<GithubUserRepository> = emptyList()
)