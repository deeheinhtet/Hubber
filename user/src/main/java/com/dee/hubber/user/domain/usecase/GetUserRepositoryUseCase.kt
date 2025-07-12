package com.dee.hubber.user.domain.usecase

import com.dee.core_network.base.BaseUseCase
import com.dee.hubber.user.domain.model.GithubUserRepository
import com.dee.hubber.user.domain.repository.UserRepository
import javax.inject.Inject

class GetUserRepositoryUseCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<String, List<GithubUserRepository>>() {
    override suspend fun invoke(params: String): Result<List<GithubUserRepository>> {
        return repository.getUserRepository(params)
    }
}