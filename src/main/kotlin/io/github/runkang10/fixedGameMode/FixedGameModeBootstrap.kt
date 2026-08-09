package io.github.runkang10.fixedGameMode

import io.github.runkang10.compactmono.services.ColoredLogger
import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.fixedGameMode.configurations.current.Settings
import io.github.runkang10.fixedGameMode.configurations.current.Translations
import io.github.runkang10.fixedGameMode.services.Commands
import io.github.runkang10.fixedGameMode.services.LoggedConfiguration
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.bootstrap.PluginBootstrap
import io.papermc.paper.plugin.bootstrap.PluginProviderContext
import org.spongepowered.configurate.ConfigurationOptions
import java.io.File

@Suppress("unused", "UnstableApiUsage")
internal class FixedGameModeBootstrap : PluginBootstrap {
    private val registry = KeyedRegistry()

    override fun bootstrap(context: BootstrapContext) {
        val logger = ColoredLogger(context.logger)
        registry.add(logger)

        val pluginFolder = context.dataDirectory.toFile()
        val settings = LoggedConfiguration(
            File(pluginFolder, "settings.conf"),
            Settings::class,
            Settings(),
            ConfigurationOptions.defaults(),
            null,
            logger
        )
        settings.load()
        registry.add(settings, "settings")
        val translations = LoggedConfiguration(
            File(pluginFolder, "translations.conf"),
            Translations::class,
            Translations(),
            ConfigurationOptions.defaults(),
            null,
            logger
        )
        translations.load()
        registry.add(translations, "translations")

        val commands = Commands(logger, context.pluginMeta, context.lifecycleManager, registry)
        commands.load()
    }

    override fun createPlugin(context: PluginProviderContext) = FixedGameMode(registry)
}
