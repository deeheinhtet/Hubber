package com.dee.authenication.data.repository

import com.dee.authenication.data.remote.api.AuthenticationApiService
import com.dee.authenication.domain.model.AccessToken
import com.dee.authenication.domain.repository.AuthenticationRepository
import com.dee.core_network.base.BaseRepository
import com.dee.core_network.base.NetworkException
import com.dee.core_network.base.NetworkResult
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: AuthenticationApiService
) : BaseRepository(), AuthenticationRepository {
    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Result<AccessToken> {
        return when (val result = execute {
            apiService.getAccessToken(
                clientId = clientId,
                clientSecret = clientSecret,
                code = code
            )
        }) {
            is NetworkResult.Success -> {
                val data = result.data
                if (!data.error.isNullOrEmpty()) {
                    Result.failure(NetworkException.UnknownException(errorMessage = data.errorDescription.orEmpty()))
                } else {
                    val token = AccessToken(
                        tokenType = data.tokenType.orEmpty(),
                        accessToken = data.accessToken.orEmpty(),
                        scope = data.scope.orEmpty()
                    )
                    Result.success(token)
                }
            }
            is NetworkResult.Error -> Result.failure(result.exception)
        }
    }
}