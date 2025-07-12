package com.dee.authenication.data.remote.api

import com.dee.authenication.data.remote.dto.AccessTokenDto
import com.dee.core_network.base.NetworkResult
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface AuthenticationApiService {
    @FormUrlEncoded
    @POST
    @Headers(
        "Accept: application/json",
        "Content-Type: application/x-www-form-urlencoded",
        "User-Agent: Hubber/1.0"
    )
    suspend fun getAccessToken(
        @Url url: String = "https://github.com/login/oauth/access_token",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Response<AccessTokenDto>

}