package io.github.runkang10.fixedGameMode.services

import io.github.runkang10.compactmono.services.ColoredLogger
import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.transformation.ConfigurationTransformation
import java.io.File
import kotlin.reflect.KClass

class LoggedConfiguration<T : Any>(
    file: File,
    type: KClass<T>,
    default: T,
    options: ConfigurationOptions,
    migrations: ConfigurationTransformation.Versioned?,
    private val logger: ColoredLogger
) : IConfiguration<T> {
    private val fileName = file.name

    private val instance = Configuration(file, type, default, options, migrations)


    override fun load(): IConfiguration.Result {
        logger.info("Loading '$fileName' configuration...")

        val result = instance.load()
        when (result) {
            is IConfiguration.Result.Success<*> -> logger.success("Loaded '$fileName' configuration.")
            is IConfiguration.Result.Failure -> logger.error(
                "Failed to load configuration from '$fileName'! Default configuration will be used.",
                result.error
            )
        }
        return result
    }

    override fun get() = instance.get()
}