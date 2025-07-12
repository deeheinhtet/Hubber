package com.dee.core_network.base

abstract class BaseUseCase<in P, out R> {
    abstract suspend operator fun invoke(params: P): Result<R>
}