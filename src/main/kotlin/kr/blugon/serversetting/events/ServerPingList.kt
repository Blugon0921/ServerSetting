package kr.blugon.serversetting.events

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import kr.blugon.serversetting.ServerSetting
import kr.blugon.serversetting.ServerSetting.Companion.effect
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*

class ServerPingList : Listener {
    @EventHandler
    fun SetMotd(event: PaperServerListPingEvent) {
        //MOTD
        if (ServerSetting.yaml.getBoolean("Motd.apply")) {
            var motd1 = ServerSetting.yaml.getString("Motd.Motd1")
            var motd2 = ServerSetting.yaml.getString("Motd.Motd2")
            if(motd1 == null) motd1 = ""
            if(motd2 == null) motd2 = ""

            event.motd(Component.text("${motd1}\n${motd2}".effect()))
        }

        //FakeVersion
        if (ServerSetting.yaml.getBoolean("FakeVersion.apply")) {
            var version = ServerSetting.yaml.getString("FakeVersion.FakeVersion")
            if(version == null) version = Bukkit.getMinecraftVersion()
            version = version.effect()
            event.version = Objects.requireNonNull(version)
        }

        //FakeMaxPlayers
        if (ServerSetting.yaml.getBoolean("FakeMaxPlayers.apply")) {
            val maxPlayer = ServerSetting.yaml.getInt("FakeMaxPlayers.FakeMaxPlayers")
            event.maxPlayers = maxPlayer
        }

        //FakePlayers
        if (ServerSetting.yaml.getBoolean("FakePlayers.apply")) {
            val player = ServerSetting.yaml.getInt("FakePlayers.FakePlayers")
            event.numPlayers = player
        }
    }
}