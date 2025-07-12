package com.dee.hubber.user.domain.model

data class UsersPageResult(
    val users: List<GitHubUser>,
    val nextSince: Long?,
    val hasMore: Boolean,
    val currentSince: Long?,
    val totalCount : Long? = null
)