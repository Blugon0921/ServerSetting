package kr.blugon.serversetting.commands

import io.github.monun.kommand.kommand
import kr.blugon.serversetting.ServerSetting.Companion.isReloadFile
import kr.blugon.serversetting.ServerSetting.Companion.isReloadYaml
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class Comand (plugin : JavaPlugin) {

    init {
        plugin.kommand {
            register("test") {
                requires {
                    hasPermission(1, "serversetting.reload")
                }

                executes {
                    Bukkit.getConsoleSender().sendMessage(text("Reloading...").color(NamedTextColor.YELLOW))
                    for(players in Bukkit.getOnlinePlayers()) {
                        if(!players.isOp) continue
                        players.sendMessage(text("Reloading...").color(NamedTextColor.YELLOW))
                    }
                    isReloadYaml.set("isReload", true)
                    isReloadYaml.save(isReloadFile)
                    Bukkit.reload()
                }
            }
        }
    }
}