package io.github.runkang10.fixedGameMode.commands

import io.github.runkang10.compactmono.commands.BrigadierCommand
import io.github.runkang10.compactmono.commands.BrigadierCommandMeta
import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.fixedGameMode.configurations.current.Translations
import io.github.runkang10.fixedGameMode.services.LoggedConfiguration
import io.github.runkang10.fixedGameMode.utilities.Permissions
import org.bukkit.GameMode

class SurvivalCommand(registry: KeyedRegistry) : BrigadierCommand {
    private val translations = registry.get<LoggedConfiguration<Translations>>("translations")


    override fun meta() = BrigadierCommandMeta("Switch gamemode to survival.", listOf("gms"))

    override fun execute() = GenericGameMode.new(
        "survival",
        Permissions.GAME_MODE.getValue(GameMode.SURVIVAL),
        GameMode.SURVIVAL,
        translations
    )
}