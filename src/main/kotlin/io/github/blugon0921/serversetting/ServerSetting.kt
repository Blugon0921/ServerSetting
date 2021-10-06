package io.github.blugon0921.serversetting

import io.github.blugon0921.serversetting.commands.Kommand
import io.github.blugon0921.serversetting.events.PlayerEvents
import io.github.blugon0921.serversetting.events.ServerPingList
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import java.awt.Color
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ServerSetting : JavaPlugin(), Listener {

    companion object {
        var configFile : File = File("plugins/ServerSetting/config.yml")
        var yaml = YamlConfiguration.loadConfiguration(configFile)

        fun Replace(replaceMessage: String): String {
            var replaceMessage = replaceMessage
            val date = Date()
            replaceMessage = replaceMessage.replace("&", "§")
            replaceMessage = replaceMessage.replace("%random%", ChatColor.of(Color((Math.random()*255).toInt(),(Math.random()*255).toInt(),(Math.random()*255).toInt())).toString()) //RandomColor
            replaceMessage = replaceMessage.replace("%version%", Bukkit.getServer().minecraftVersion) //Version
            replaceMessage = replaceMessage.replace("%years%", SimpleDateFormat("yyyy").format(date)) //Years
            replaceMessage = replaceMessage.replace("%month%", SimpleDateFormat("MM").format(date)) //Month
            replaceMessage = replaceMessage.replace("%day%", SimpleDateFormat("dd").format(date)) //Days
            replaceMessage = replaceMessage.replace("%hour%", SimpleDateFormat("HH").format(date)) //Hour
            replaceMessage = replaceMessage.replace("%minute%", SimpleDateFormat("mm").format(date)) //Minutes
            replaceMessage = replaceMessage.replace("%second%", SimpleDateFormat("ss").format(date)) //Seconds
            return replaceMessage
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