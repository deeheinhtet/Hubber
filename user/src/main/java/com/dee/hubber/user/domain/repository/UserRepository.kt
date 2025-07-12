package com.dee.hubber.user.domain.repository

import com.dee.hubber.user.data.remote.dto.GitHubRepositoryDto
import com.dee.hubber.user.data.remote.dto.GitHubUserDto
import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.model.GithubUserRepository
import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.model.UsersPageResult

interface UserRepository {
    suspend fun getUsers(request: GithubUserRequest): Result<UsersPageResult>
    suspend fun getUserDetails(username: String): Result<GitHubUser>
    suspend fun getCurrentUserDetails(): Result<GitHubUser>
    suspend fun searchUsers(request: GithubUserRequest): Result<UsersPageResult>
    suspend fun getUserRepository(username: String) : Result<List<GithubUserRepository>>
    suspend fun getCurrentUserRepository() : Result<List<GithubUserRepository>>
}