package com.dee.authenication.domain.usecase

import com.dee.authenication.domain.model.AccessToken
import com.dee.authenication.domain.model.AccessTokenRequest
import com.dee.authenication.domain.repository.AuthenticationRepository
import com.dee.common.AppSharedPreference
import com.dee.core_network.base.BaseUseCase
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val repository: AuthenticationRepository,
    private val appSharedPreference: AppSharedPreference
) : BaseUseCase<AccessTokenRequest?, AccessToken>() {
    override suspend fun invoke(params: AccessTokenRequest?): Result<AccessToken> {
        val result = repository.getAccessToken(
            clientId = params?.clientId.orEmpty(),
            clientSecret = params?.clientSecret.orEmpty(),
            code = params?.code.orEmpty(),
        )
        if (result.isSuccess) {
            appSharedPreference.token = (result.getOrNull()?.accessToken)
        }
        return result
    }
}