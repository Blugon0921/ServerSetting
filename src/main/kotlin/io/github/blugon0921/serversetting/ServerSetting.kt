package io.github.blugon0921.serversetting

import io.github.blugon0921.serversetting.commands.Kommand
import io.github.blugon0921.serversetting.events.PlayerEvents
import io.github.blugon0921.serversetting.events.ServerPingList
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
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
        var configFile : File = File("plugins/ServerSetting/config.yml")
        var yaml = YamlConfiguration.loadConfiguration(configFile)

        fun Replace(replaceMessage: String): String {
            var replace_message = replaceMessage
            val date = Date()
            replace_message = replace_message.replace("&", "§")
            replace_message = replace_message.replace("%random%", ChatColor.of(Color((Math.random()*255).toInt(),(Math.random()*255).toInt(),(Math.random()*255).toInt())).toString()) //RandomColor
            replace_message = replace_message.replace("%version%", Bukkit.getServer().minecraftVersion) //Version
            replace_message = replace_message.replace("%years%", SimpleDateFormat("yyyy").format(date)) //Years
            replace_message = replace_message.replace("%month%", SimpleDateFormat("MM").format(date)) //Month
            replace_message = replace_message.replace("%day%", SimpleDateFormat("dd").format(date)) //Days
            replace_message = replace_message.replace("%hour%", SimpleDateFormat("HH").format(date)) //Hour
            replace_message = replace_message.replace("%minute%", SimpleDateFormat("mm").format(date)) //Minutes
            replace_message = replace_message.replace("%second%", SimpleDateFormat("ss").format(date)) //Seconds
            return replace_message
        }
    }


    override fun onEnable() {
        logger.info("Plugin Enable")
        Bukkit.getPluginManager().registerEvents(this, this)
        Bukkit.getPluginManager().registerEvents(ServerPingList(), this)
        Bukkit.getPluginManager().registerEvents(PlayerEvents(), this)

        this.getCommand("load")!!.apply {
            setExecutor(Kommand())
            tabCompleter = Kommand()
        }


        Bukkit.getScheduler().scheduleSyncDelayedTask(this, { Bukkit.broadcast(Component.text("${ChatColor.GREEN}Reload Complate!")) }, 1)

        if(!configFile.exists()) {
            saveConfig()
            config.options().copyDefaults(true)
            saveConfig()
        }
    }


    override fun onDisable() {
        if(!configFile.exists()) {
            saveConfig()
            config.options().copyDefaults(true)
            saveConfig()
        }
        logger.info("Plugin Disable")
    }
}