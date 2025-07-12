package com.dee.hubber.utils.network

import com.dee.common.AppSharedPreference
import com.dee.hubber.utils.AuthEventManager
import okhttp3.Interceptor
import okhttp3.Response


class TokenExpiryInterceptor(
    private val appSharedPreference: AppSharedPreference,
    private val authEventManager: AuthEventManager,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            401, 403 -> {
                appSharedPreference.token = null
                authEventManager.emitTokenExpired()
            }
        }
        return response
    }
}