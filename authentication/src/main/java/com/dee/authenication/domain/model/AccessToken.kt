package com.dee.authenication.domain.model

data class AccessToken(
    val accessToken: String,
    val tokenType: String,
    val scope: String
)

data class AccessTokenRequest(
    val clientSecret: String,
    val clientId: String,
    val code: String
)