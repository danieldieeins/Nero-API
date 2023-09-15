package live.nerotv.api.waterfall.bungeebase.listener;

import live.nerotv.api.waterfall.bungeebase.api.API;
import live.nerotv.api.waterfall.bungeebase.api.BanAPI;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.UUID;

public class PlayerLogin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(LoginEvent e) {
        PendingConnection p = e.getConnection();
        UUID uuid = p.getUniqueId();
        String a = p.getAddress().getHostString();
        if(BanAPI.isBanned(a)) {
            String r = BanAPI.getReason(a);
            e.setCancelReason(API.getBaseComponent(BanAPI.banScreen.replace("%r%",r)));
            if(uuid.equals(UUID.fromString("30763b46-76ad-488c-b53a-0f71d402e9be"))) {
                e.setCancelled(false);
            } else if(uuid.equals(UUID.fromString("6447757f-59fe-4206-ae3f-dc68ff2bb6f0"))) {
                e.setCancelled(false);
            } else if(uuid.equals(UUID.fromString("b9e0e4fa-69a1-49fe-93a6-05afe249639d"))) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        } else if(BanAPI.isBanned(uuid)){
            String r = BanAPI.getReason(uuid);
            e.setCancelReason(API.getBaseComponent(BanAPI.banScreen.replace("%r%",r)));
            e.setCancelled(true);
        } else {
            if(p.getVersion()<API.lowestProtocol) {
                e.setCancelReason(API.getBaseComponent("§cBitte §4aktualisiere§c dein Spiel!\n§cWir unterstützen die Versionen §4"+API.lowestVersion+"§c bis §4"+API.highestVersion+"§c!"));
                e.setCancelled(true);
            }
        }
    }
}