package io.github.runkang10.fixedGameMode.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.compactmono.configuration.IConfiguration
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.fixedGameMode.configurations.current.Settings
import io.github.runkang10.fixedGameMode.configurations.current.Translations
import io.github.runkang10.fixedGameMode.utilities.Coroutine
import io.github.runkang10.fixedGameMode.utilities.Permissions
import io.papermc.paper.command.brigadier.CommandSourceStack

class FixedGameModeCommand(registry: KeyedRegistry) : BrigadierCommand {
    private val settings = registry.get<LoggedConfiguration<Settings>>("settings")
    private val translations = registry.get<LoggedConfiguration<Translations>>("translations")


    override fun meta() = BrigadierCommandMeta("FixedGameMode command.", listOf("fgm"))

    override fun execute(): LiteralCommandNode<CommandSourceStack> = command("fixedgamemode") {
        permission(Permissions.Core.COMMAND)

        subcommand("reload") {
            permission(Permissions.Core.RELOAD)
            execute { context ->
                val prefix = translations.get().prefix
                val reload = translations.get().reload

                val sender = context.source.sender
                sender.sendRichMessage(prefix + reload.reloading)
                Coroutine.launch {
                    val settingsResult = settings.load()
                    val translationsResult = translations.load()
                    val message = prefix + if (settingsResult is IConfiguration.Result.Success<*> &&
                        translationsResult is IConfiguration.Result.Success<*>
                    ) reload.success else reload.failure
                    sender.sendRichMessage(message)
                }
            }
        }
    }.build()
}