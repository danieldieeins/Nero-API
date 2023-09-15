package live.nerotv.api.waterfall.bungeebase.listener;

import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.waterfall.utils.user.BungeeUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChangeServer implements Listener {

    @EventHandler
    public void onServerChange(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        BungeeUser u = BungeeNero.getAPI().getOnlineUser(p.getUniqueId());
        u.setInit(u.getInit()+1);
        String serverName = e.getPlayer().getServer().getInfo().getName();
        if(u.getInit()>=1) {
            p.sendMessage("§8»§7 Du bist nun auf §e" + serverName + "§8!");
        } else {
            p.sendMessage("§8» §a"+p.getName());
        }
    }
}