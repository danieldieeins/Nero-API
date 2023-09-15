package live.nerotv.api.paper.listeners;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.paper.utils.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerConnectListener implements Listener {

    @EventHandler
    public void onConnect(AsyncPlayerPreLoginEvent e) {
        User u = Nero.getAPI().getOnlineUser(e.getUniqueId());
        if (!Nero.getNeroServer().isStopping()) {
            if (u.isBanned()) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "§cDu wurdest vom §4nerotvlive Netzwerk §cgebannt§8.\n\n§7Grund§8: §c%br%\n\n§7Du kannst auf unserem Discord-Server einen Entbannungsantrag stellen.\n§9https://discord.gg/RX7hGa6wSc");
                u.disconnect("§cDu bist gebannt§8.");
            }
        } else {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,"§cDer Server startet zurzeit neu§8...");
        }
    }
}