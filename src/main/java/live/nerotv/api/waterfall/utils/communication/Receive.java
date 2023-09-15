package live.nerotv.api.waterfall.utils.communication;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Receive implements Listener {

    @EventHandler
    public void onReceive(PluginMessageEvent e) {
        if ( !e.getTag().equalsIgnoreCase("base:spigot")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("bungee")) {
            if (e.getReceiver() instanceof ProxiedPlayer) {
                ProxiedPlayer receiver = (ProxiedPlayer) e.getReceiver();
            }
            if (e.getReceiver() instanceof Server) {
                Server receiver = (Server) e.getReceiver();
            }
        }
    }
}