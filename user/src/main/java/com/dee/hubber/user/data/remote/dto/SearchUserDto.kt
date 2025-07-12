package com.dee.hubber.user.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchUserDto(
    @Json(name = "total_count") val totalCount: Long,
    @Json(name = "items") val items: List<GitHubUserDto> = emptyList(),
)