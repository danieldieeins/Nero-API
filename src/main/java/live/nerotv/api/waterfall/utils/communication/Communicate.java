package live.nerotv.api.waterfall.utils.communication;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;

public class Communicate {

    public static void sendToSpigot(ProxiedPlayer player,String data1,int data2) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if ( networkPlayers == null || networkPlayers.isEmpty() )
        {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("spigot");
        out.writeUTF( data1 );
        out.writeInt( data2 );
        player.getServer().getInfo().sendData( "base:bungee",out.toByteArray());
    }

    public static void sendSound(ProxiedPlayer player,String sound) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if ( networkPlayers == null || networkPlayers.isEmpty() )
        {
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("playSound_"+sound);
        out.writeUTF(player.getName());
        out.writeInt(1);
        player.getServer().getInfo().sendData( "base:bungee",out.toByteArray());
    }

    public static void sendStop() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("stopServer");
        for(ServerInfo servers : ProxyServer.getInstance().getServers().values()) {
            servers.sendData( "base:bungee",out.toByteArray());
        }
    }
}