package io.github.runkang10.fixedGameMode.packetListeners

import com.github.retrooper.packetevents.event.PacketListenerAbstract
import com.github.retrooper.packetevents.event.PacketListenerPriority
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChangeGameMode
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityStatus
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSystemChatMessage
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import io.github.runkang10.compactmono.configuration.LoggedConfiguration
import io.github.runkang10.compactmono.services.KeyedRegistry
import io.github.runkang10.compactmono.services.schedulers.EntityScheduler
import io.github.runkang10.fixedGameMode.configurations.current.Translations
import io.github.runkang10.fixedGameMode.utilities.Permissions
import io.github.runkang10.fixedGameMode.utilities.TagResolvers
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit

class GameModePacketListener(
    private val registry: KeyedRegistry
) : PacketListenerAbstract(PacketListenerPriority.NORMAL) {
    private val entityScheduler = registry.get<EntityScheduler>()

    private val gameModePermissions = Permissions.GAME_MODE.mapValues { it.value.self }
    private val opRange = 24..28

    private val errorComponent = Component.textOfChildren(
        Component.translatable("debug.prefix")
            .decorate(TextDecoration.BOLD)
            .color(NamedTextColor.YELLOW)
            .appendSpace(),
        Component.translatable("debug.gamemodes.error"),
    )

    private val translations get() = registry.get<LoggedConfiguration<Translations>>("translations").get()


    override fun onPacketSend(event: PacketSendEvent?) {
        if (event == null) return
        if (event.packetType != PacketType.Play.Server.ENTITY_STATUS) return

        val wrapper = WrapperPlayServerEntityStatus(event)
        val level = wrapper.status
        if (level in opRange && level < 26) wrapper.status = 26
    }

    override fun onPacketReceive(event: PacketReceiveEvent?) {
        if (event == null) return
        if (event.packetType != PacketType.Play.Client.CHANGE_GAME_MODE) return

        event.isCancelled = true

        val wrapper = WrapperPlayClientChangeGameMode(event)
        val gameMode = SpigotConversionUtil.toBukkitGameMode(wrapper.gameMode)
        val permission = gameModePermissions.getValue(gameMode)

        val user = event.user
        val player = Bukkit.getPlayer(user.uuid)
        if (player?.hasPermission(permission) == true) entityScheduler.run(player, null) {
            player.gameMode = gameMode
            player.sendRichMessage(translations.prefix + translations.changed.self, TagResolvers.gameMode(gameMode))
        } else {
            val errorMessage = WrapperPlayServerSystemChatMessage(false, errorComponent)
            user.sendPacketSilently(errorMessage)
        }
    }
}