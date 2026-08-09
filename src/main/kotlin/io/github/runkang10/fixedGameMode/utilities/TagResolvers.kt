package io.github.runkang10.fixedGameMode.utilities

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.GameMode

object TagResolvers {
    fun gameMode(
        type: GameMode,
        target: String = "N/A"
    ) = TagResolver.resolver(
        Placeholder.parsed("gamemode", type.name.lowercase()),
        Placeholder.parsed("target", target)
    )
}