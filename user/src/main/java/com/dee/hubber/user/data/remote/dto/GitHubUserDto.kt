package com.dee.hubber.user.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubUserDto(
    @Json(name = "login") val login: String,
    @Json(name = "id") val id: Long,
    @Json(name = "avatar_url") val avatarUrl: String,
    @Json(name = "name") val name: String? = null,
    @Json(name = "followers") val followers: Int = 0,
    @Json(name = "following") val following: Int = 0,
    @Json(name = "public_repos") val publicRepos: Int = 0,
    @Json(name = "bio") val bio: String? = null,
    @Json(name = "html_url") val htmlUrl: String? = null
)