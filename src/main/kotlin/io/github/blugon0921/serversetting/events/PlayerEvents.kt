package io.github.blugon0921.serversetting.events

import io.github.blugon0921.serversetting.ServerSetting
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import io.github.blugon0921.serversetting.ServerSetting.Companion.Replace
import io.github.blugon0921.serversetting.ServerSetting.Companion.yaml
import org.bukkit.entity.HumanEntity
import org.bukkit.event.player.PlayerToggleSneakEvent

class PlayerEvents : Listener {


    @EventHandler
    fun chatFormat(event: PlayerChatEvent) {
        if (yaml.getBoolean("ChatFormat.apply")) {
            if (yaml.contains("ChatFormat.ChatFormat")) {
                event.isCancelled = true
                var message = yaml.getString("ChatFormat.ChatFormat")!!
                message = Replace(message)
                message = message.replace("%player%", event.player.name)
                message = message.replace("%message%", event.message)
                Bukkit.broadcast(Component.text(message))
            } else {
                yaml["ChatFormat.ChatFormat"] = "&f<%player%> %message%"
            }
        }
    }

    @EventHandler
    fun SystemMessage(event: PlayerJoinEvent) {
        if (yaml.getBoolean("SystemMessage.apply")) {
            if (yaml.contains("SystemMessage.JoinMessage")) {
                var message = yaml.getString("SystemMessage.JoinMessage")!!
                message = Replace(message)
                message = message.replace("%player%", event.player.name)
                event.joinMessage(Component.text(message))
            } else {
                yaml["SystemMessage.JoinMessage"] = "&e%player% joined the game!"
            }
        }
    }

    @EventHandler
    fun SystemMessage(event: PlayerQuitEvent) {
        if (yaml.getBoolean("SystemMessage.apply")) {
            if (yaml.contains("SystemMessage.QuitMessage")) {
                var message = yaml.getString("SystemMessage.QuitMessage")!!
                message = Replace(message)
                message = message.replace("%player%", event.player.name)
                event.quitMessage(Component.text(message))
            } else {
                yaml["SystemMessage.QuitMessage"] = "&e%player% leaved the game!"
            }
        }
    }
}