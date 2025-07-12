package com.dee.hubber.user.data.remote.api

import com.dee.common.Constants
import com.dee.core_network.base.NetworkResult
import com.dee.hubber.user.data.remote.dto.GitHubRepositoryDto
import com.dee.hubber.user.data.remote.dto.GitHubUserDto
import com.dee.hubber.user.data.remote.dto.SearchUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Long? = null,
        @Query("q") query: String? = null,
        @Query("per_page") perPage: Int = Constants.USER_PER_PAGE
    ): Response<List<GitHubUserDto>>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int = Constants.USER_PER_PAGE
    ): Response<SearchUserDto>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") name: String,
    ): Response<GitHubUserDto>

    @GET("user")
    suspend fun getCurrentUserDetails(
    ): Response<GitHubUserDto>

    @GET("user/repos?type=owner&sort=updated&direction=desc")
    suspend fun getCurrentUserRepository(
    ): Response<List<GitHubRepositoryDto>>

    @GET("users/{username}/repos?type=owner&sort=updated&direction=desc")
    suspend fun getUserRepository(
        @Path("username") name: String,
    ): Response<List<GitHubRepositoryDto>>

}