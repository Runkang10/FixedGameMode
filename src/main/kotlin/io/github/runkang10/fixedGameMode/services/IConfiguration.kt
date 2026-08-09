package io.github.runkang10.fixedGameMode.services

interface IConfiguration<T : Any> {
    sealed interface Result {
        data class Success<out T : Any>(
            val data: T,
            val migrated: Boolean
        ) : Result

        data class Failure(val error: Throwable) : Result
    }

    fun load(): Result

    fun get(): T
}