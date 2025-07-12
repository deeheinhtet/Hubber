package com.dee.core_network.di

import android.content.Context
import com.dee.core_network.utils.NetworkConfigs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        configs: NetworkConfigs
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(configs.connectTimeoutSec, TimeUnit.SECONDS)
            .writeTimeout(configs.writeTimeoutSec, TimeUnit.SECONDS)
            .readTimeout(configs.readTimeoutSec, TimeUnit.SECONDS)
            .callTimeout(configs.callTimeoutSec, TimeUnit.SECONDS)
            .cache(
                Cache(
                    directory = File(context.cacheDir, "http_cache"),
                    maxSize = 50L * 1024L * 1024L
                )
            )
        configs.interceptors().forEach {
            client.addInterceptor(it)
        }
        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        configs: NetworkConfigs,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(configs.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit
    }
}