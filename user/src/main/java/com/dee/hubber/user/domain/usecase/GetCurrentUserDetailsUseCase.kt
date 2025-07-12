package com.dee.hubber.user.domain.usecase

import com.dee.core_network.base.BaseUseCase
import com.dee.hubber.user.domain.model.GitHubUser
import com.dee.hubber.user.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserDetailsUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<Unit?, GitHubUser>() {
    override suspend fun invoke(params: Unit?): Result<GitHubUser> {
        return repository.getCurrentUserDetails()
    }
}