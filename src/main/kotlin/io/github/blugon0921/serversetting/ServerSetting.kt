package io.github.blugon0921.serversetting

import io.github.blugon09.pluginhelper.component.component
import io.github.blugon0921.serversetting.commands.Kommand
import io.github.blugon0921.serversetting.events.PlayerEvents
import io.github.blugon0921.serversetting.events.ServerPingList
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Server
import org.bukkit.command.SimpleCommandMap
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.SimplePluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.awt.Color
import java.io.File
import java.lang.reflect.Field
import java.text.SimpleDateFormat
import java.util.*

class ServerSetting : JavaPlugin(), Listener {

    companion object {
        var configFile = File(ServerSetting().dataFolder, "config.yml")
        var yaml = YamlConfiguration.loadConfiguration(configFile)

        var isReloadFile = File(ServerSetting().dataFolder, "isReload")
        var isReloadYaml = YamlConfiguration.loadConfiguration(isReloadFile)

        fun saveSettingConfig() {
            if(!configFile.exists()) {
                ServerSetting().saveConfig()
                ServerSetting().config.options().copyDefaults(true)
                ServerSetting().saveConfig()
            }
        }

        fun String.effect() : String {
            var replace = this
            val date = Date()
            replace = replace.replace("&", "§")
            replace = replace.replace("%random%", ChatColor.of(Color((Math.random()*255).toInt(),(Math.random()*255).toInt(),(Math.random()*255).toInt())).toString()) //RandomColor
            replace = replace.replace("%version%", Bukkit.getServer().minecraftVersion) //Version
            replace = replace.replace("%years%", SimpleDateFormat("yyyy").format(date)) //Years
            replace = replace.replace("%month%", SimpleDateFormat("MM").format(date)) //Month
            replace = replace.replace("%day%", SimpleDateFormat("dd").format(date)) //Days
            replace = replace.replace("%hour%", SimpleDateFormat("HH").format(date)) //Hour
            replace = replace.replace("%minute%", SimpleDateFormat("mm").format(date)) //Minutes
            replace = replace.replace("%second%", SimpleDateFormat("ss").format(date)) //Seconds
            return replace
        }
    }


    override fun onEnable() {
        logger.info("Plugin Enable")
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(ServerPingList(), this)
        Bukkit.getPluginManager().registerEvents(PlayerEvents(), this)
        getCommand("load")!!.apply {
            setExecutor(Kommand())
            tabCompleter = Kommand()
        }

        saveSettingConfig()

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, {
            if(isReloadYaml["isReload"] == true) {
                Bukkit.broadcast("Reload Complate!".component().color(NamedTextColor.GREEN))
                isReloadYaml["isReload"] = false
            } else {
                isReloadYaml["isReload"] = false
            }
        }, 1)
    }


    override fun onDisable() {
        logger.info("Plugin Disable")
        saveSettingConfig()
    }
}