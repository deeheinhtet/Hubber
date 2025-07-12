package com.dee.core_network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GitHubErrorResponse(
    val message: String,
    val documentation_url: String? = null,
    val errors: List<GitHubError>? = null
)

@JsonClass(generateAdapter = true)
data class GitHubError(
    val resource: String? = null,
    val field: String? = null,
    val code: String? = null,
    val message: String? = null
)
