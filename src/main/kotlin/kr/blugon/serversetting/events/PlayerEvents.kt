package kr.blugon.serversetting.events

import kr.blugon.serversetting.ServerSetting
import kr.blugon.serversetting.ServerSetting.Companion.effect
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerEvents : Listener {
    @EventHandler
    fun chatFormat(event: PlayerChatEvent) {
        if (ServerSetting.yaml.getBoolean("ChatFormat.apply")) {
            var message = ServerSetting.yaml.getString("ChatFormat.ChatFormat") ?: return
            event.isCancelled = true
            message = message.effect()
            message = message.replace("%player%", event.player.name)
            message = message.replace("%message%", event.message)
            Bukkit.broadcast(Component.text(message))
        }
    }

    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        if (ServerSetting.yaml.getBoolean("SystemMessage.apply")) {
            var message = ServerSetting.yaml.getString("SystemMessage.JoinMessage") ?: return
            message = message.effect()
            message = message.replace("%player%", event.player.name)
            event.joinMessage(Component.text(message))
        }
    }

    @EventHandler
    fun playerQuit(event: PlayerQuitEvent) {
        if (ServerSetting.yaml.getBoolean("SystemMessage.apply")) {
            var message = ServerSetting.yaml.getString("SystemMessage.QuitMessage") ?: return
            message = message.effect()
            message = message.replace("%player%", event.player.name)
            event.quitMessage(Component.text(message))
        }
    }
}