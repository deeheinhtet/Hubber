package com.dee.authenication.data.di

import com.dee.authenication.data.remote.api.AuthenticationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideAuthenticationApiService(retrofit: Retrofit) =
        retrofit.create(AuthenticationApiService::class.java)
}