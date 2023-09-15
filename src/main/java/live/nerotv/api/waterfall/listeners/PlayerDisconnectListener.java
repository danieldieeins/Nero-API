package live.nerotv.api.waterfall.listeners;

import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.waterfall.utils.user.BungeeUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerDisconnectListener implements Listener {

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e) {
        ProxiedPlayer p = e.getPlayer();
        BungeeUser u = BungeeNero.getAPI().getOnlineUser(p.getUniqueId());
        u.disconnect();
    }
}