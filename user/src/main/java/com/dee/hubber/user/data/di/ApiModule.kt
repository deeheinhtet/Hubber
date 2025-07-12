package com.dee.hubber.user.data.di

import com.dee.hubber.user.data.remote.api.UserApiService
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
    fun provideUserApiService(retrofit: Retrofit) = retrofit.create(UserApiService::class.java)

}