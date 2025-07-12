package com.dee.hubber.user.domain.usecase

import com.dee.core_network.base.BaseUseCase
import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.model.UsersPageResult
import com.dee.hubber.user.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<GithubUserRequest, UsersPageResult>() {
    override suspend fun invoke(params: GithubUserRequest): Result<UsersPageResult> {
        return if (params.query.isNullOrEmpty()) {
            repository.getUsers(params)
        } else {
            repository.searchUsers(params)
        }
    }
}