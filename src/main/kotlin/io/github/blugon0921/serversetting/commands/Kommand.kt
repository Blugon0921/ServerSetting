package io.github.blugon0921.serversetting.commands

import net.kyori.adventure.text.Component
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class Kommand : CommandExecutor,TabCompleter {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (command.name == "load") {
            if (sender.isOp) {
                if (args.isEmpty()) {
                    Bukkit.broadcast(Component.text("${ChatColor.YELLOW}Reloading..."))
                    Bukkit.reload()
                } else if (args.size == 1) {
                    if (args[0] == "confirm") {
                        Bukkit.broadcast(Component.text("${ChatColor.YELLOW}Reloading..."))
                        Bukkit.reload()
                    } else {
                        sender.sendMessage("${ChatColor.RED}알 수 없거나 불완전한 명령어입니다.")
                    }
                } else {
                    sender.sendMessage("${ChatColor.RED}알 수 없거나 불완전한 명령어입니다.")
                }
            } else {
                sender.sendMessage("${ChatColor.RED}알 수 없거나 불완전한 명령어입니다.")
            }
        }
        return false
    }



    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String>? {
        return if (command.name == "load") {
            if (sender.isOp) {
                if (args.size == 1) {
                    listOf("confirm")
                } else {
                    emptyList()
                }
            } else {
                emptyList()
            }
        } else null
    }
}