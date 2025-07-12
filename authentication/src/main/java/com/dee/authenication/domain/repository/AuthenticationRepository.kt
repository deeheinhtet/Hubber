package com.dee.authenication.domain.repository

import com.dee.authenication.domain.model.AccessToken

interface AuthenticationRepository {


    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Result<AccessToken>

}