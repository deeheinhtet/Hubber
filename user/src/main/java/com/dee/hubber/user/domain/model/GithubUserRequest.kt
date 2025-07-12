package com.dee.hubber.user.domain.model

import com.dee.common.Constants

data class GithubUserRequest(
    val since: Long? = null,
    val query: String? = null,
    val perPage: Int = Constants.USER_PER_PAGE,
    val page : Int = 1
)