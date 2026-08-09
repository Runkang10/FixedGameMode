package io.github.runkang10.fixedGameMode.extensions

import org.bukkit.plugin.PluginManager

fun PluginManager.isPluginLoaded(plugin: String) = getPlugin(plugin) != null