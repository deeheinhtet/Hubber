package com.dee.hubber.di

import android.content.Context
import com.dee.common.AppSharedPreference
import com.dee.core_network.utils.NetworkConfigs
import com.dee.hubber.BuildConfig
import com.dee.hubber.utils.AuthEventManager
import com.dee.hubber.utils.network.DefaultNetworkConfigs
import com.dee.hubber.utils.security.NativeKeysManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    @Named("BASE_URL")
    fun provideBaseUrl(): String = "https://api.github.com/"

    @Singleton
    @Provides
    @Named("DEFAULT_CLIENT_TOKEN")
    fun provideClientToken() = NativeKeysManager.getClientToken()

    @Singleton
    @Provides
    @Named("GITHUB_CLIENT_SECRET")
    fun provideClientSecret() = NativeKeysManager.getClientSecret()

    @Singleton
    @Provides
    @Named("GITHUB_CLIENT_ID")
    fun provideClientId() = NativeKeysManager.getClientId()

    @Singleton
    @Provides
    @Named("GITHUB_REDIRECT_URL")
    fun provideGithubRedirectUrl() = BuildConfig.GITHUB_CALLBACK_URL

    @Provides
    @Singleton
    fun provideAuthEventManager(): AuthEventManager = AuthEventManager()

    @Singleton
    @Provides
    fun provideNetworkConfigs(
        @Named("BASE_URL") url: String,
        appSharedPreference: AppSharedPreference,
        @ApplicationContext context: Context,
        authEventManager: AuthEventManager
    ): NetworkConfigs {
        return DefaultNetworkConfigs(url, appSharedPreference, context, authEventManager)
    }
}