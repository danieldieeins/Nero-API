package live.nerotv.api.paper.listeners;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.paper.utils.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        Nero.getAPI().connectUser(uuid);
        User u = Nero.getAPI().getOnlineUser(uuid);
    }
}