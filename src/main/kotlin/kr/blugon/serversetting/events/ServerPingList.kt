package kr.blugon.serversetting.events

import com.destroystokyo.paper.event.server.PaperServerListPingEvent
import kr.blugon.serversetting.ServerSetting.Companion.effect
import kr.blugon.serversetting.ServerSetting.Companion.yaml
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*

class ServerPingList : Listener {
    @EventHandler
    fun SetMotd(event: PaperServerListPingEvent) {
        //MOTD
        if (yaml.getBoolean("Motd.apply")) {
            var motd1 = yaml.getString("Motd.Motd1")
            var motd2 = yaml.getString("Motd.Motd2")
            if(motd1 == null) motd1 = ""
            if(motd2 == null) motd2 = ""

            event.motd(text("${motd1}\n${motd2}".effect()))
        }

        //FakeVersion
        if (yaml.getBoolean("FakeVersion.apply")) {
            var version = yaml.getString("FakeVersion.FakeVersion")
            if(version == null) version = Bukkit.getMinecraftVersion()
            version = version.effect()
            event.version = Objects.requireNonNull(version)
        }

        //FakeMaxPlayers
        if (yaml.getBoolean("FakeMaxPlayers.apply")) {
            val maxPlayer = yaml.getInt("FakeMaxPlayers.FakeMaxPlayers")
            event.maxPlayers = maxPlayer
        }

        //FakePlayers
        if (yaml.getBoolean("FakePlayers.apply")) {
            val player = yaml.getInt("FakePlayers.FakePlayers")
            event.numPlayers = player
        }
    }
}