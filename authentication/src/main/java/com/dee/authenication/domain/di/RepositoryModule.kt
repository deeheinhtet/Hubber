package com.dee.authenication.domain.di

import com.dee.authenication.data.repository.AuthenticationRepositoryImpl
import com.dee.authenication.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(repositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}