package live.nerotv.api.paper.listeners;

import live.nerotv.api.paper.events.NeroChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(PlayerChatEvent e) {
        e.setCancelled(true);
        NeroChatEvent event = new NeroChatEvent(e.getPlayer(),e.getMessage());
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            Bukkit.broadcastMessage(event.getFormat());
        }
    }
}