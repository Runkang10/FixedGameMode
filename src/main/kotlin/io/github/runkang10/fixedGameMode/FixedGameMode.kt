package io.github.runkang10.fixedGameMode

import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.compactmono.services.schedulers.EntityScheduler
import io.github.runkang10.fixedGameMode.services.PacketListeners
import io.github.runkang10.fixedGameMode.utilities.Coroutine
import io.github.runkang10.fixedGameMode.utilities.Permissions
import org.bukkit.plugin.java.JavaPlugin

class FixedGameMode(private val registry: KeyedRegistry) : JavaPlugin() {
    private val packetListeners by lazy { PacketListeners(registry) }


    override fun onLoad() {
        registry.add(EntityScheduler(this))

        Permissions.register()
        packetListeners.load()
    }

    override fun onDisable() {
        packetListeners.unload()
        Coroutine.cancel()

        registry.clear()
    }
}
