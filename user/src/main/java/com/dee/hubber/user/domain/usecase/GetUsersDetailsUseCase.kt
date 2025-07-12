package com.dee.hubber.user.domain.usecase

import com.dee.core_network.base.BaseUseCase
import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.model.GithubUserRequest
import com.dee.hubber.user.domain.model.UsersPageResult
import com.dee.hubber.user.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<String, GitHubUser>() {
    override suspend fun invoke(params: String): Result<GitHubUser> {
        return repository.getUserDetails(username = params)
    }
}