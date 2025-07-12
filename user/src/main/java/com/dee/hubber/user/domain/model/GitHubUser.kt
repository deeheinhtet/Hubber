package com.dee.hubber.user.domain.model

data class GitHubUser(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val name: String?,
    val followers: Int,
    val following: Int,
    val publicRepos: Int,
    val bio: String?,
    val htmlUrl: String?
)
