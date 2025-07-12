package com.dee.hubber.utils.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.dee.common.AppSharedPreference
import com.dee.core_network.utils.NetworkConfigs
import com.dee.hubber.utils.AuthEventManager
import okhttp3.Interceptor

class DefaultNetworkConfigs(
    private val url: String,
    private val prefs: AppSharedPreference,
    private val context: Context,
    private val authEventManager: AuthEventManager,

    ) : NetworkConfigs {
    override fun interceptors(): List<Interceptor> {
        return listOf(
            TokenExpiryInterceptor(
                appSharedPreference = prefs,
                authEventManager = authEventManager
            ),
            AuthHeaderInterceptor(prefs),
            ChuckerInterceptor(context),
        )
    }

    override val baseUrl: String
        get() = url
    override val connectTimeoutSec: Long
        get() = 5
    override val readTimeoutSec: Long
        get() = 5
    override val callTimeoutSec: Long
        get() = 10
    override val writeTimeoutSec: Long
        get() = 5
}