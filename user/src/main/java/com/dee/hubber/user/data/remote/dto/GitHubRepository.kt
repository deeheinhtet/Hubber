package com.dee.hubber.user.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubRepositoryDto(
    val id: Long,
    @Json(name = "full_name") val fullName: String? = null,
    @Json(name = "html_url") val repoUrl: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "language") val language: String? = null,
    @Json(name = "forks_count") val forkCount: Int? = null,
    @Json(name = "watchers_count") val watcherCount: Int? = null,
    @Json(name = "stargazers_count") val starCount: Int? = null,
)

