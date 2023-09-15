package live.nerotv.api.paper.listeners;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.paper.utils.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        User u = Nero.getAPI().getOnlineUser(uuid);
        Nero.getAPI().disconnectUser(u);
    }
}