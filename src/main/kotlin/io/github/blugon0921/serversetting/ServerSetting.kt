package io.github.blugon0921.serversetting

import io.github.blugon0921.serversetting.commands.Kommand
import io.github.blugon0921.serversetting.events.PlayerEvents
import io.github.blugon0921.serversetting.events.ServerPingList
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
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

    private val startMotdText = ChatColor.AQUA.toString() + "plugins/Motd에 들어가서 config.yml을 수정해주세요"


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
        save_Config()
    }


    override fun onDisable() {
        save_Config()
        logger.info("Plugin Disable")
    }




    fun save_Config() {
        if (!configFile.exists()) {
            yaml.set("Motd.Motd1", "&7[&9Motd&7] $startMotdText")
            yaml.set("Motd.Motd2", "&7[&9Motd&7] $startMotdText")
            yaml.set("Motd.apply", true)

            yaml.set("FakeVersion.FakeVersion", "&f%version%")
            yaml.set("FakeVersion.apply", false)

            yaml.set("ChatFormat.ChatFormat", "&f<%player%> %message%")
            yaml.set("ChatFormat.apply", false)

            yaml.set("FakeMaxPlayers.FakeMaxPlayers", Bukkit.getMaxPlayers())
            yaml.set("FakeMaxPlayers.apply", false)

            yaml.set("FakePlayers.FakePlayers", Bukkit.getOnlinePlayers().size)
            yaml.set("FakePlayers.apply", false)

            yaml.set("SystemMessage.JoinMessage", "&e%player% joined the game!")
            yaml.set("SystemMessage.QuitMessage", "&e%player% leaved the game!")
            yaml.set("SystemMessage.apply", true)
        }
        yaml.save(configFile)
    }
}