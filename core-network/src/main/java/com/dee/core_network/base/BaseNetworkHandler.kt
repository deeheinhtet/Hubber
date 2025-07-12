package com.dee.core_network.base

import com.dee.core_network.model.GitHubErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseNetworkHandler {

    protected suspend fun <T> safeApiCall(
        dispatcher: kotlinx.coroutines.CoroutineDispatcher = Dispatchers.IO,
        apiCall: suspend () -> Response<T>
    ): NetworkResult<T> {
        return withContext(dispatcher) {
            try {
                val response = apiCall()
                handleResponse(response)
            } catch (exception: Exception) {
                handleException(exception)
            }
        }
    }

    inline fun <reified T> T.hasApiError(): String? {
        return try {
            val errorField = this!!::class.java.getDeclaredField("error")
            errorField.isAccessible = true
            errorField.get(this) as? String
        } catch (e: Exception) {
            null
        }
    }


    private fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        return when {
            response.isSuccessful -> {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error(
                        NetworkException.SerializationException("Response body is null")
                    )
                }
            }

            response.code() == 401 -> {
                NetworkResult.Error(
                    NetworkException.UnauthorizedException(
                        parseErrorMessage(response.errorBody()?.string())
                            ?: "Invalid or missing GitHub token"
                    )
                )
            }

            response.code() == 403 -> {
                val errorBody = response.errorBody()?.string()
                val isRateLimit = errorBody?.contains("rate limit", ignoreCase = true) == true

                if (isRateLimit) {
                    val resetTime = response.headers()["X-RateLimit-Reset"]?.toLongOrNull()
                    val remaining = response.headers()["X-RateLimit-Remaining"]?.toIntOrNull() ?: 0

                    NetworkResult.Error(
                        NetworkException.RateLimitException(
                            resetTime = resetTime,
                            remaining = remaining,
                            errorMessage = parseErrorMessage(errorBody)
                                ?: "GitHub API rate limit exceeded"
                        )
                    )
                } else {
                    NetworkResult.Error(
                        NetworkException.ForbiddenException(
                            parseErrorMessage(errorBody)
                                ?: "Access forbidden - Check your permissions"
                        )
                    )
                }
            }

            response.code() == 404 -> {
                NetworkResult.Error(
                    NetworkException.NotFoundException(
                        parseErrorMessage(response.errorBody()?.string())
                            ?: "Resource not found"
                    )
                )
            }

            response.code() in 500..599 -> {
                NetworkResult.Error(
                    NetworkException.ServerException(
                        code = response.code(),
                        errorMessage = parseErrorMessage(response.errorBody()?.string())
                            ?: "Server error - Please try again later"
                    )
                )
            }

            else -> {
                NetworkResult.Error(
                    NetworkException.HttpException(
                        code = response.code(),
                        errorMessage = parseErrorMessage(response.errorBody()?.string())
                            ?: "HTTP error ${response.code()}",
                        errorBody = response.errorBody()?.string()
                    )
                )
            }
        }
    }

    private fun <T> handleException(exception: Exception): NetworkResult<T> {
        return NetworkResult.Error(
            when (exception) {
                is UnknownHostException -> {
                    NetworkException.NoInternetException("No internet connection")
                }

                is SocketTimeoutException -> {
                    NetworkException.TimeoutException("Request timed out")
                }

                is IOException -> {
                    NetworkException.ConnectionException("Network connection failed: ${exception.message}")
                }

                is HttpException -> {
                    NetworkException.HttpException(
                        code = exception.code(),
                        errorMessage = exception.message() ?: "HTTP error"
                    )
                }

                is JSONException -> {
                    NetworkException.SerializationException("Failed to parse JSON response")
                }

                else -> {
                    NetworkException.UnknownException("Unknown error: ${exception.message}")
                }
            }
        )
    }

    private fun parseErrorMessage(errorBody: String?): String? {
        return try {
            if (errorBody != null) {
                val moshi = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
                val adapter = moshi.adapter(GitHubErrorResponse::class.java)
                val errorResponse = adapter.fromJson(errorBody)
                errorResponse?.message
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}