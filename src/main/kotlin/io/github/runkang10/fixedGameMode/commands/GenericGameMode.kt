package io.github.runkang10.fixedGameMode.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.github.runkang10.compactmono.commands.*
import io.github.runkang10.fixedGameMode.configurations.current.Translations
import io.github.runkang10.fixedGameMode.services.IConfiguration
import io.github.runkang10.fixedGameMode.utilities.Permissions
import io.github.runkang10.fixedGameMode.utilities.TagResolvers
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import org.bukkit.GameMode
import org.bukkit.entity.Player

object GenericGameMode {
    fun new(
        name: String,
        permissions: Permissions.PermissionPair,
        gameMode: GameMode,
        translations: IConfiguration<Translations>
    ): LiteralCommandNode<CommandSourceStack> = command(name) {
        permission(permissions.self)
        argument("target", ArgumentTypes.players()) {
            permission(permissions.others)
            execute { execute(it, gameMode, translations) }
        }
        execute { execute(it, gameMode, translations) }
    }.build()

    private fun execute(
        context: ContextSourceStack,
        gameMode: GameMode,
        translations: IConfiguration<Translations>
    ) {
        val source = context.source
        val sender = source.sender
        val targets = runCatching {
            context.getArgument<PlayerSelectorArgumentResolver>("target")?.resolve(source)
        }.getOrNull()

        val translations = translations.get()
        val prefix = translations.prefix

        if (sender !is Player && targets == null) {
            sender.sendRichMessage(prefix + translations.missingTarget)
            return
        }

        if (sender is Player && targets == null) {
            sender.gameMode = gameMode
            sender.sendRichMessage(prefix + translations.changed.self, TagResolvers.gameMode(gameMode))
            return
        }

        targets?.forEach { target ->
            target.gameMode = gameMode
            val tags = TagResolvers.gameMode(gameMode, target.name)
            if (sender != target) sender.sendRichMessage(prefix + translations.changed.others, tags)
            target.sendRichMessage(prefix + translations.changed.self, tags)
        }
    }
}