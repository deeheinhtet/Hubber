package com.dee.hubber.user.domain.model

data class GithubUserRepository(
    val id: Long,
    val star: Int,
    val forkCount: Int,
    val watcherCount: Int,
    val repoUrl: String,
    val repoName: String,
    val repoDescription: String,
    val language : String
)
