package kr.blugon.serversetting

import io.github.monun.kommand.kommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class LoadCommand(plugin : JavaPlugin) {

    init {
        plugin.kommand {
            "load" {
                requires {
                    hasPermission(4, "serversetting.reload")
                }

                executes {
                    Bukkit.getConsoleSender().sendMessage(
                        Component.text("Reloading...")
                            .color(NamedTextColor.YELLOW))
                    for(players in Bukkit.getOnlinePlayers()) {
                        if(!players.isOp) continue
                        players.sendMessage(Component.text("Reloading...").color(NamedTextColor.YELLOW))
                    }
                    ServerSetting.isReloadYaml.set("isReload", true)
                    ServerSetting.isReloadYaml.save(ServerSetting.isReloadFile)
                    Bukkit.reload()
                }
            }
        }
    }
}