package kr.blugon.serversetting

import kr.blugon.pluginutils.component.MiniColor
import kr.blugon.pluginutils.component.MiniColor.Companion.miniMessage
import kr.blugon.serversetting.ServerSetting.Companion.effect
import kr.blugon.serversetting.events.PlayerEvents
import kr.blugon.serversetting.events.ServerPingList
import net.kyori.adventure.text.Component
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
                ServerSetting().apply {
                    this.saveConfig()
                    this.config.options().copyDefaults(true)
                    this.saveConfig()
                }
            }
            if(!isReloadFile.exists()) {
                isReloadYaml.save(isReloadFile)
                isReloadYaml.set("isReload", false)
                isReloadYaml.save(isReloadFile)
            }
        }

        fun String.effect() : Component {
            val date = Date()
            return this
                .replace("&0", MiniColor.BLACK.toString())
                .replace("&1", MiniColor.DARK_BLUE.toString())
                .replace("&2", MiniColor.DARK_GREEN.toString())
                .replace("&3", MiniColor.DARK_AQUA.toString())
                .replace("&4", MiniColor.DARK_RED.toString())
                .replace("&5", MiniColor.DARK_PURPLE.toString())
                .replace("&6", MiniColor.GOLD.toString())
                .replace("&7", MiniColor.GRAY.toString())
                .replace("&8", MiniColor.DARK_GRAY.toString())
                .replace("&9", MiniColor.BLUE.toString())
                .replace("&a", MiniColor.GREEN.toString())
                .replace("&b", MiniColor.AQUA.toString())
                .replace("&c", MiniColor.RED.toString())
                .replace("&d", MiniColor.LIGHT_PURPLE.toString())
                .replace("&e", MiniColor.YELLOW.toString())
                .replace("&f", MiniColor.WHITE.toString())
                .replace("&k", MiniColor.OBFUSCATED.toString())
                .replace("&l", MiniColor.BOLD.toString())
                .replace("&m", MiniColor.STRIKETHROUGH.toString())
                .replace("&n", MiniColor.UNDERLINE.toString())
                .replace("&o", MiniColor.ITALIC.toString())
                .replace("&r", MiniColor.RESET.toString())
                .replace("%random%", MiniColor.of(randomColor)) //RandomColor
                .replace("%version%", Bukkit.getServer().minecraftVersion) //Version
                .replace("%year%", SimpleDateFormat("yyyy").format(date)) //Years
                .replace("%month%", SimpleDateFormat("MM").format(date)) //Month
                .replace("%day%", SimpleDateFormat("dd").format(date)) //Days
                .replace("%hour%", SimpleDateFormat("HH").format(date)) //Hour
                .replace("%minute%", SimpleDateFormat("mm").format(date)) //Minutes
                .replace("%second%", SimpleDateFormat("ss").format(date)) //Seconds
                .miniMessage
        }

        fun String.chatEffect() : String {
            val date = Date()
            return this
                .replace("\\&", "ª")
                .replace("&", "§")
                .replace("ª", "&")
                .replace("%random%", ChatColor.of(Color((Math.random()*255).toInt(),(Math.random()*255).toInt(),(Math.random()*255).toInt())).toString()) //RandomColor
                .replace("%version%", Bukkit.getServer().minecraftVersion) //Version
                .replace("%years%", SimpleDateFormat("yyyy").format(date)) //Years
                .replace("%month%", SimpleDateFormat("MM").format(date)) //Month
                .replace("%day%", SimpleDateFormat("dd").format(date)) //Days
                .replace("%hour%", SimpleDateFormat("HH").format(date)) //Hour
                .replace("%minute%", SimpleDateFormat("mm").format(date)) //Minutes
                .replace("%second%", SimpleDateFormat("ss").format(date)) //Seconds
        }

        val randomColor: String
            get() {
                val random = Random()
                val nextInt = random.nextInt(0xffffff + 1)
                val colorCode = String.format("#%06x", nextInt)
                return colorCode
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