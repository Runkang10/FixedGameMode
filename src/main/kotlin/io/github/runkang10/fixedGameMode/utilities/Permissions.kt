package io.github.runkang10.fixedGameMode.utilities

import org.bukkit.GameMode
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionDefault
import org.bukkit.util.permissions.DefaultPermissions

object Permissions {
    data class PermissionPair(
        val self: String,
        val others: String
    )

    val GAME_MODE = GameMode.entries.associateWith { type ->
        val self = "fixedgamemode.${type.name.lowercase()}"
        val others = "$self.others"
        PermissionPair(self, others)
    }

    object Core {
        const val COMMAND = "fixedgamemode.command"
        const val RELOAD = "$COMMAND.reload"
    }

    fun register() {
        GAME_MODE.values.forEach { pair ->
            val self = Permission(pair.self)
            val others = Permission(pair.others)
            DefaultPermissions.registerPermission(self)
            DefaultPermissions.registerPermission(others)
        }

        listOf(Core.COMMAND, Core.RELOAD).forEach { permission ->
            DefaultPermissions.registerPermission(Permission(permission, PermissionDefault.OP))
        }
    }
}