package com.dee.hubber.utils.network

import com.dee.common.AppSharedPreference
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor(
    private val prefs: AppSharedPreference
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Accept", "application/vnd.github.v3+json")
        if (!prefs.token.isNullOrEmpty()) {
            newRequest
                .addHeader("Authorization", "token ${prefs.token}")
        }
        val response = chain.proceed(newRequest.build())
        return response
    }
}