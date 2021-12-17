package io.github.blugon0921.serversetting.commands

import io.github.blugon09.pluginhelper.component.component
import io.github.blugon0921.serversetting.ServerSetting.Companion.isReloadYaml
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

class Kommand : CommandExecutor,TabCompleter {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (command.name == "load") {
            if (sender.isOp) {
                if (args.isNotEmpty()) return false
                Bukkit.broadcast("${ChatColor.YELLOW}Reloading...".component())
                isReloadYaml["isReload"] = true
                Bukkit.reload()
            } else {
                sender.sendMessage("${ChatColor.RED}권한이 부족합니다.")
            }
        }
        return false
    }


    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String>? {
        return Collections.emptyList()
    }
}