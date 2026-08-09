package io.github.runkang10.fixedGameMode.services

import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.hocon.HoconConfigurationLoader
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.extensions.set
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.loader.HeaderMode
import org.spongepowered.configurate.transformation.ConfigurationTransformation
import java.io.File
import kotlin.io.path.createDirectories
import kotlin.reflect.KClass

class Configuration<T : Any>(
    private val file: File,
    private val type: KClass<T>,
    private val default: T,
    private val options: ConfigurationOptions,
    private val migrations: ConfigurationTransformation.Versioned?
) : IConfiguration<T> {
    private val loader = HoconConfigurationLoader.builder()
        .file(file)
        .emitComments(true)
        .prettyPrinting(true)
        .indent(2)
        .defaultOptions { options ->
            options.serializers { builder ->
                builder.registerAnnotatedObjects(objectMapperFactory())
            }
        }
        .headerMode(HeaderMode.PRESERVE)
        .build()

    private var data: T = default


    override fun load(): IConfiguration.Result = try {
        if (!file.exists()) save(defaultNode())

        val node = loader.load(options)

        val migrated = if (migrations != null) {
            val preMigrationVersion = migrations.version(node)
            migrations.apply(node)
            val postMigrationVersion = migrations.version(node)
            save(node)

            postMigrationVersion > preMigrationVersion
        } else false

        data = node.get(type, default)

        IConfiguration.Result.Success(data, migrated)
    } catch (e: Exception) {
        data = default

        IConfiguration.Result.Failure(e)
    }

    override fun get() = data


    private fun defaultNode() = loader.createNode(options).set(type, default)

    private fun save(node: CommentedConfigurationNode) {
        file.parentFile?.mkdirs()
        loader.save(node)
    }
}