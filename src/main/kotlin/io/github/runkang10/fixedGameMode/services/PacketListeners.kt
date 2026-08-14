package io.github.runkang10.fixedGameMode.services

import com.github.retrooper.packetevents.PacketEvents
import io.github.runkang10.compactmono.services.ColoredLogger
import io.github.runkang10.compactmono.services.GenericService
import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.fixedGameMode.packetListeners.GameModePacketListener
import org.bukkit.Bukkit

class PacketListeners(registry: KeyedRegistry) : GenericService {
    private val logger = registry.get<ColoredLogger>()
    private val packetListeners by lazy { arrayOf(GameModePacketListener(registry)) }

    override fun load() {
        logger.loading("Packet listeners")

        PacketEvents.getAPI().eventManager.registerListeners(*packetListeners)

        logger.loaded("Packet listeners")
    }

    override fun unload() {
        val pluginManager = Bukkit.getPluginManager()
        if (!pluginManager.isPluginEnabled("packetevents")) return

        logger.unloading("Packet listeners")

        PacketEvents.getAPI().eventManager.unregisterListeners(*packetListeners)

        logger.unloaded("Packet listeners")
    }
}