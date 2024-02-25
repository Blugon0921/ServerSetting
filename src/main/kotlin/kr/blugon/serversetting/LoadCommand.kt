package kr.blugon.serversetting

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class LoadCommand(plugin : JavaPlugin) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(!sender.isOp) {
            sender.sendMessage(text("권한이 부족합니다").color(NamedTextColor.RED))
            return false
        }
        if(args.isNotEmpty()) {
            sender.sendMessage(text("인수가 많습니다").color(NamedTextColor.RED))
            return false
        }
        Bukkit.getConsoleSender().sendMessage(
            text("Reloading...").color(NamedTextColor.YELLOW))
        for(players in Bukkit.getOnlinePlayers()) {
            if(!players.isOp) continue
            players.sendMessage(text("Reloading...").color(NamedTextColor.YELLOW))
        }
        ServerSetting.isReloadYaml.set("isReload", true)
        ServerSetting.isReloadYaml.save(ServerSetting.isReloadFile)
        Bukkit.reload()
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return mutableListOf()
    }
}