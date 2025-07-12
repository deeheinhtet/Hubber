package com.dee.core_network.base

import retrofit2.Response

abstract class BaseRepository : BaseNetworkHandler() {

    protected suspend fun <T> execute(
        apiCall: suspend () -> Response<T>
    ): NetworkResult<T> {
        return when (val result = safeApiCall { apiCall() }) {
            is NetworkResult.Success -> {
                try {
                    NetworkResult.Success(result.data)
                } catch (e: Exception) {
                    NetworkResult.Error(
                        NetworkException.SerializationException("Data mapping failed: ${e.message}")
                    )
                }
            }

            is NetworkResult.Error -> result
        }
    }
}