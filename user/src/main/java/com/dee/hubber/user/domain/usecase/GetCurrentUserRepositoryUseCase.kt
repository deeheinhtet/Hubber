package com.dee.hubber.user.domain.usecase

import com.dee.core_network.base.BaseUseCase
import com.dee.hubber.user.domain.model.GithubUserRepository
import com.dee.hubber.user.domain.repository.UserRepository
import javax.inject.Inject

class GetCurrentUserRepositoryUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<Unit?, List<GithubUserRepository>>() {
    override suspend fun invoke(params: Unit?): Result<List<GithubUserRepository>> {
        return repository.getCurrentUserRepository()
    }
}