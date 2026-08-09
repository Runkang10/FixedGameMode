package io.github.runkang10.fixedGameMode.configurations.current

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class Settings(
    @Comment("DO NOT CHANGE THIS")
    val version: Int = VERSION,
) {
    companion object {
        const val VERSION = 1
    }
}
