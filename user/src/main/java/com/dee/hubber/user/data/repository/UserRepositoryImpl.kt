package com.dee.hubber.user.data.repository

import com.dee.common.Constants
import com.dee.core_network.base.BaseRepository
import com.dee.core_network.base.NetworkResult
import com.dee.hubber.user.data.remote.api.UserApiService
import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.model.GithubUserRepository
import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.model.UsersPageResult
import com.dee.hubber.user.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : BaseRepository(), UserRepository {
    override suspend fun getUsers(request: GithubUserRequest): Result<UsersPageResult> {
        return when (val result = execute {
            apiService.getUsers(
                since = request.since,
                query = request.query,
                perPage = request.perPage
            )
        }) {
            is NetworkResult.Success -> {
                val users = result.data
                val nextSince = if (users.isNotEmpty()) users.last().id else null
                val hasMore = users.size == Constants.USER_PER_PAGE
                val userPageResult = UsersPageResult(
                    users = users.map { dto ->
                        GitHubUser(
                            login = dto.login,
                            id = dto.id,
                            avatarUrl = dto.avatarUrl,
                            name = dto.name,
                            followers = dto.followers,
                            following = dto.following,
                            publicRepos = dto.publicRepos,
                            bio = dto.bio,
                            htmlUrl = dto.htmlUrl
                        )
                    },
                    nextSince = nextSince,
                    hasMore = hasMore,
                    currentSince = request.since
                )
                Result.success(userPageResult)
            }

            is NetworkResult.Error -> Result.failure(result.exception)
        }
    }

    override suspend fun getUserDetails(username: String): Result<GitHubUser> {
        return when (val result = execute {
            apiService.getUserDetails(
                username
            )
        }) {
            is NetworkResult.Success -> {
                val dto = result.data
                val gitHubUser = GitHubUser(
                    login = dto.login,
                    id = dto.id,
                    avatarUrl = dto.avatarUrl,
                    name = dto.name,
                    followers = dto.followers,
                    following = dto.following,
                    publicRepos = dto.publicRepos,
                    bio = dto.bio,
                    htmlUrl = dto.htmlUrl
                )
                Result.success(gitHubUser)
            }

            is NetworkResult.Error -> Result.failure(result.exception)
        }
    }

    override suspend fun getCurrentUserDetails(): Result<GitHubUser> {
        return when (val result = execute {
            apiService.getCurrentUserDetails()
        }) {
            is NetworkResult.Success -> {
                val dto = result.data
                val gitHubUser = GitHubUser(
                    login = dto.login,
                    id = dto.id,
                    avatarUrl = dto.avatarUrl,
                    name = dto.name,
                    followers = dto.followers,
                    following = dto.following,
                    publicRepos = dto.publicRepos,
                    bio = dto.bio,
                    htmlUrl = dto.htmlUrl
                )
                Result.success(gitHubUser)
            }

            is NetworkResult.Error -> Result.failure(result.exception)
        }
    }

    override suspend fun searchUsers(request: GithubUserRequest): Result<UsersPageResult> {
        return when (val result = execute {
            apiService.searchUser(
                query = request.query,
                perPage = request.perPage,
                page = request.page
            )
        }) {
            is NetworkResult.Success -> {
                val response = result.data
                val users = response.items
                val nextSince = if (users.isNotEmpty()) users.last().id else null
                val hasMore = users.size == Constants.USER_PER_PAGE
                val userPageResult = UsersPageResult(
                    users = users.map { dto ->
                        GitHubUser(
                            login = dto.login,
                            id = dto.id,
                            avatarUrl = dto.avatarUrl,
                            name = dto.name,
                            followers = dto.followers,
                            following = dto.following,
                            publicRepos = dto.publicRepos,
                            bio = dto.bio,
                            htmlUrl = dto.htmlUrl
                        )
                    },
                    nextSince = nextSince,
                    hasMore = hasMore,
                    currentSince = request.since,
                    totalCount = response.totalCount
                )
                Result.success(userPageResult)
            }

            is NetworkResult.Error -> Result.failure(result.exception)
        }
    }

    override suspend fun getUserRepository(username: String): Result<List<GithubUserRepository>> {
        return when (val result = execute {
            apiService.getUserRepository(
                username
            )
        }) {
            is NetworkResult.Success -> {
                val userRepo = result.data.map {
                    GithubUserRepository(
                        id = it.id,
                        repoName = it.fullName.orEmpty(),
                        star = it.starCount ?: 0,
                        forkCount = it.forkCount ?: 0,
                        repoDescription = it.description.orEmpty(),
                        repoUrl = it.repoUrl.orEmpty(),
                        language = it.language ?: "",
                        watcherCount = it.watcherCount ?: 0
                    )
                }
                Result.success(userRepo)
            }

            is NetworkResult.Error -> Result.failure(result.exception)
        }
    }

    override suspend fun getCurrentUserRepository(): Result<List<GithubUserRepository>> {
        return when (val result = execute {
            apiService.getCurrentUserRepository()
        }) {
            is NetworkResult.Success -> {
                val userRepo = result.data.map {
                    GithubUserRepository(
                        id = it.id,
                        repoName = it.fullName.orEmpty(),
                        star = it.starCount ?: 0,
                        forkCount = it.forkCount ?: 0,
                        repoDescription = it.description.orEmpty(),
                        repoUrl = it.repoUrl.orEmpty(),
                        language = it.language ?: "",
                        watcherCount = it.watcherCount ?: 0
                    )
                }
                Result.success(userRepo)
            }

            is NetworkResult.Error -> Result.failure(result.exception)
        }
    }
}