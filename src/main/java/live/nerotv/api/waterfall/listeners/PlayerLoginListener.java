package live.nerotv.api.waterfall.listeners;

import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.waterfall.utils.user.BungeeUser;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerLoginListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(LoginEvent e) {
        PendingConnection p = e.getConnection();
        BungeeUser u = BungeeNero.getAPI().getOnlineUser(p.getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPostLogin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        BungeeUser u = BungeeNero.getAPI().getOnlineUser(p.getUniqueId());
    }
}