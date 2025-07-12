package com.dee.hubber.user.domain.di

import com.dee.hubber.user.data.repository.UserRepositoryImpl
import com.dee.hubber.user.domain.repository.UserRepository
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
    abstract fun bindUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository
}