package kr.blugon.serversetting.events

import kr.blugon.serversetting.ServerSetting
import kr.blugon.serversetting.ServerSetting.Companion.chatEffect
import kr.blugon.serversetting.ServerSetting.Companion.effect
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class PlayerEvents : Listener {
    @EventHandler
    fun chatFormat(event: AsyncPlayerChatEvent) {
        if (ServerSetting.yaml.getBoolean("ChatFormat.apply")) {
            var message = ServerSetting.yaml.getString("ChatFormat.ChatFormat") ?: return
            message = message.chatEffect()
            message = message.replace("%player%", "%1\$s")
            message = message.replace("%message%", "%2\$s")
            event.format = message
        }
    }

    @EventHandler
    fun playerJoin(event: PlayerJoinEvent) {
        if (ServerSetting.yaml.getBoolean("SystemMessage.apply")) {
            val message = ServerSetting.yaml.getString("SystemMessage.JoinMessage")?.replace("%player%", event.player.name) ?: return
            event.joinMessage(message.effect())
        }
    }

    @EventHandler
    fun playerQuit(event: PlayerQuitEvent) {
        if (ServerSetting.yaml.getBoolean("SystemMessage.apply")) {
            val message = ServerSetting.yaml.getString("SystemMessage.QuitMessage")?.replace("%player%", event.player.name) ?: return
            event.quitMessage(message.effect())
        }
    }
}