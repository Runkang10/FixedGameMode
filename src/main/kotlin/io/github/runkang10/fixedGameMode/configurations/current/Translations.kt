package io.github.runkang10.fixedGameMode.configurations.current

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class Translations(
    @Comment("DO NOT CHANGE THIS")
    val version: Int = VERSION,
    val prefix: String = "<color:#00ff88><b>[FixedGameMode]</b></color> ",
    val missingTarget: String = "<red>Please specify a valid target!",
    val changed: ChangedTranslation = ChangedTranslation(
        "<green>Set own game mode to <yellow><gamemode></yellow> mode.",
        "<green>Set <aqua><target></aqua>'s game mode to <yellow><gamemode></yellow> mode."
    ),
    val reload: ReloadTranslation = ReloadTranslation()
) {
    companion object {
        const val VERSION = 1
    }

    @ConfigSerializable
    data class ChangedTranslation(
        val self: String = "N/A",
        val others: String = "N/A"
    )

    @ConfigSerializable
    data class ReloadTranslation(
        val reloading: String = "<yellow>Reloading configurations...",
        val success: String = "<aqua>Reloaded configurations.",
        val failure: String = "<red>Failed to reload configurations! Check console logs for more details."
    )
}