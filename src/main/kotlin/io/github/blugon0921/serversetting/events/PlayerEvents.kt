package io.github.blugon0921.serversetting.events

import io.github.blugon0921.serversetting.ServerSetting
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import io.github.blugon0921.serversetting.ServerSetting.Companion.effect
import io.github.blugon0921.serversetting.ServerSetting.Companion.yaml
import org.bukkit.entity.HumanEntity
import org.bukkit.event.player.*

class PlayerEvents : Listener {


    @EventHandler
    fun chatFormat(event: PlayerChatEvent) {
        if (yaml.getBoolean("ChatFormat.apply")) {
            var message = yaml.getString("ChatFormat.ChatFormat") ?: return
            event.isCancelled = true
            message = message.effect()
            message = message.replace("%player%", event.player.name)
            message = message.replace("%message%", event.message)
            Bukkit.broadcast(Component.text(message))
        }
    }

    @EventHandler
    fun SystemMessage(event: PlayerJoinEvent) {
        if (yaml.getBoolean("SystemMessage.apply")) {
            var message = yaml.getString("SystemMessage.JoinMessage") ?: return
            message = message.effect()
            message = message.replace("%player%", event.player.name)
            event.joinMessage(Component.text(message))
        }
    }

    @EventHandler
    fun SystemMessage(event: PlayerQuitEvent) {
        if (yaml.getBoolean("SystemMessage.apply")) {
            var message = yaml.getString("SystemMessage.QuitMessage") ?: return
            message = message.effect()
            message = message.replace("%player%", event.player.name)
            event.quitMessage(Component.text(message))
        }
    }
}