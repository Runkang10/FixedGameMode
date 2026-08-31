package io.github.runkang10.fixedGameMode.commands

import io.github.runkang10.compactmono.commands.BrigadierCommand
import io.github.runkang10.compactmono.commands.BrigadierCommandMeta
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.fixedGameMode.configurations.current.Translations
import io.github.runkang10.fixedGameMode.utilities.Permissions
import org.bukkit.GameMode

class CreativeCommand(registry: KeyedRegistry) : BrigadierCommand {
    private val translations = registry.get<LoggedConfiguration<Translations>>("translations")


    override fun meta() = BrigadierCommandMeta("Switch gamemode to creative.", listOf("gmc"))

    override fun execute() = GenericGameMode.new(
        "creative",
        Permissions.GAME_MODE.getValue(GameMode.CREATIVE),
        GameMode.CREATIVE,
        translations
    )
}