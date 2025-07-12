package com.dee.core_network.utils

import okhttp3.Interceptor

interface NetworkConfigs {
    fun interceptors(): List<Interceptor>
    val baseUrl: String
    val connectTimeoutSec: Long
    val readTimeoutSec: Long
    val callTimeoutSec: Long
    val writeTimeoutSec: Long
}