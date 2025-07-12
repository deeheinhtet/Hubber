
package com.dee.core_network.base

sealed class NetworkException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    // Network connectivity issues
    data class NoInternetException(
        val errorMessage: String = "No internet connection"
    ) : NetworkException(errorMessage)

    data class TimeoutException(
        val errorMessage: String = "Request timed out"
    ) : NetworkException(errorMessage)

    data class ConnectionException(
        val errorMessage: String = "Connection failed"
    ) : NetworkException(errorMessage)

    // HTTP errors
    data class HttpException(
        val code: Int,
        val errorMessage: String,
        val errorBody: String? = null
    ) : NetworkException("HTTP $code: $errorMessage")

    // Authentication & Authorization
    data class UnauthorizedException(
        val errorMessage: String = "Authentication required - Check your token"
    ) : NetworkException(errorMessage)

    data class ForbiddenException(
        val errorMessage: String = "Access forbidden - Insufficient permissions"
    ) : NetworkException(errorMessage)

    data class RateLimitException(
        val resetTime: Long? = null,
        val remaining: Int = 0,
        val errorMessage: String = "API rate limit exceeded"
    ) : NetworkException(errorMessage)

    data class NotFoundException(
        val errorMessage: String = "Resource not found"
    ) : NetworkException(errorMessage)

    // Data processing
    data class SerializationException(
        val errorMessage: String = "Failed to parse response data"
    ) : NetworkException(errorMessage)

    data class ValidationException(
        val field: String? = null,
        val errorMessage: String = "Invalid input data"
    ) : NetworkException(errorMessage)

    // Server errors
    data class ServerException(
        val code: Int,
        val errorMessage: String = "Server error - Please try again later"
    ) : NetworkException(errorMessage)

    // Unknown
    data class UnknownException(
        val errorMessage: String = "An unexpected error occurred"
    ) : NetworkException(errorMessage)
}