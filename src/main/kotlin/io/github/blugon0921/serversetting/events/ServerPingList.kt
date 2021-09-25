package io.github.blugon0921.serversetting.events

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*
import io.github.blugon0921.serversetting.ServerSetting.Companion.Replace
import io.github.blugon0921.serversetting.ServerSetting.Companion.yaml

class ServerPingList : Listener {



    @EventHandler
    fun SetMotd(event: PaperServerListPingEvent) {
        //MOTD
        if (yaml.getBoolean("Motd.apply")) {
            if (yaml.contains("Motd.Motd1")) {
                if (yaml.contains("Motd.Motd2")) {
                    var motd1 = yaml.getString("Motd.Motd1")!!
                    var motd2 = yaml.getString("Motd.Motd2")!!
                    motd1 = Replace(motd1)
                    motd2 = Replace(motd2)
                    event.motd(Component.text("${motd1}\n$motd2"))
                } else {
                    yaml["Motd.Motd2"] = "&f"
                }
            } else {
                yaml["Motd.Motd1"] = "&f"
            }
        }

        //FakeVersion
        if (yaml.getBoolean("FakeVersion.apply")) {
            if (yaml.contains("FakeVersion.FakeVersion")) {
                var version = yaml.getString("FakeVersion.FakeVersion")!!
                version = Replace(version)
                event.version = Objects.requireNonNull(version)
            } else {
                yaml["FakeVersion.FakeVersion"] = "&f%version%"
            }
        }

        //FakeMaxPlayers
        if (yaml.getBoolean("FakeMaxPlayers.apply")) {
            if (yaml.contains("FakeMaxPlayers.FakeMaxPlayers")) {
                val version = yaml.getInt("FakeMaxPlayers.FakeMaxPlayers")
                event.maxPlayers = version
            } else {
                yaml["FakeMaxPlayers.FakeMaxPlayers"] = Bukkit.getMaxPlayers()
            }
        }

        //FakePlayers
        if (yaml.getBoolean("FakePlayers.apply")) {
            if (yaml.contains("FakePlayers.FakePlayers")) {
                val version = yaml.getInt("FakePlayers.FakePlayers")
                event.numPlayers = version
            } else {
                yaml["FakePlayers.FakePlayers"] = Bukkit.getOnlinePlayers().size
            }
        }
    }
}