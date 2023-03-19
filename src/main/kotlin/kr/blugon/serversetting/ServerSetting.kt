package kr.blugon.serversetting

import kr.blugon.serversetting.events.PlayerEvents
import kr.blugon.serversetting.events.ServerPingList
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Color
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ServerSetting : JavaPlugin(),Listener {

    companion object {
        var configFile = File("plugins/ServerSetting/config.yml")
        var yaml = YamlConfiguration.loadConfiguration(configFile)

        var isReloadFile = File("plugins/ServerSetting/isReload.yml")
        var isReloadYaml = YamlConfiguration.loadConfiguration(isReloadFile)

        fun saveSettingConfig() {
            if(!configFile.exists()) {
                ServerSetting().saveConfig()
                ServerSetting().config.options().copyDefaults(true)
                ServerSetting().saveConfig()
            }
            if(!isReloadFile.exists()) {
                isReloadYaml.save(isReloadFile)
                isReloadYaml.set("isReload", false)
                isReloadYaml.save(isReloadFile)
            }
        }

        fun String.effect() : String {
            var string = this
            val date = Date()
            string = string.replace("\\&", "ª")
            string = string.replace("&", "§")
            string = string.replace("ª", "&")
            string = string.replace("%random%", ChatColor.of(Color((Math.random()*255).toInt(),(Math.random()*255).toInt(),(Math.random()*255).toInt())).toString()) //RandomColor
            string = string.replace("%version%", Bukkit.getServer().minecraftVersion) //Version
            string = string.replace("%years%", SimpleDateFormat("yyyy").format(date)) //Years
            string = string.replace("%month%", SimpleDateFormat("MM").format(date)) //Month
            string = string.replace("%day%", SimpleDateFormat("dd").format(date)) //Days
            string = string.replace("%hour%", SimpleDateFormat("HH").format(date)) //Hour
            string = string.replace("%minute%", SimpleDateFormat("mm").format(date)) //Minutes
            string = string.replace("%second%", SimpleDateFormat("ss").format(date)) //Seconds
            return string
        }
    }

    override fun onEnable() {
        logger.info("Plugin Enable")
        Bukkit.getPluginManager().registerEvents(PlayerEvents(), this)
        Bukkit.getPluginManager().registerEvents(ServerPingList(), this)
        getCommand("load")!!.apply {
            setExecutor(LoadCommand(this@ServerSetting))
            tabCompleter = LoadCommand(this@ServerSetting)
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, {
            if(isReloadYaml.getBoolean("isReload")) {
                Bukkit.getConsoleSender().sendMessage(text("Reload Complate!").color(NamedTextColor.GREEN))
                for(players in Bukkit.getOnlinePlayers()) {
                    if(!players.isOp) continue
                    players.sendMessage(text("Reload Complate!").color(NamedTextColor.GREEN))
                }
                isReloadYaml.set("isReload", false)
                isReloadYaml.save(isReloadFile)
            } else {
                isReloadYaml.set("isReload", false)
                isReloadYaml.save(isReloadFile)
            }
        }, 1)
    }

    override fun onDisable() {
        logger.info("Plugin Disable")
        saveSettingConfig()
    }
}