package io.github.runkang10.fixedGameMode.services

import io.github.runkang10.compactmono.services.ColoredLogger
import io.github.runkang10.compactmono.services.GenericService
import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.fixedGameMode.commands.*
import io.papermc.paper.plugin.bootstrap.BootstrapContext
import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents

@Suppress("UnstableApiUsage")
class Commands(
    private val logger: ColoredLogger,
    private val pluginMeta: PluginMeta,
    private val lifecycle: LifecycleEventManager<BootstrapContext>,
    registry: KeyedRegistry
) : GenericService {
    private val commands by lazy {
        arrayOf(
            FixedGameModeCommand(registry),
            AdventureCommand(registry),
            CreativeCommand(registry),
            SpectatorCommand(registry),
            SurvivalCommand(registry)
        )
    }

    override fun load() {
        logger.loading("Commands")

        lifecycle.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val registrar = event.registrar()
            commands.forEach { command ->
                val (description, aliases) = command.meta()
                registrar.register(pluginMeta, command.execute(), description, aliases)
            }
        }

        logger.loaded("Commands")
    }

    override fun unload() {}
}