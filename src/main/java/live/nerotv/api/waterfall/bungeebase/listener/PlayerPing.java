package live.nerotv.api.waterfall.bungeebase.listener;

import live.nerotv.api.waterfall.bungeebase.BungeeBase;
import live.nerotv.api.waterfall.bungeebase.api.API;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerPing implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players players = ping.getPlayers();
        int Protocol = e.getConnection().getVersion();
        ServerPing.Protocol vers = ping.getVersion();
        if(Protocol == 4) {
            ping.setDescriptionComponent(API.getBaseComponent("Beschreibung ausstehend..."));
            ping.setVersion(new ServerPing.Protocol("§c" + API.lowestVersion + "-" + API.highestVersion, 1));
            vers.setName("§4NeroFall " + BungeeBase.getVersion());
        } else if(Protocol < API.lowestProtocol) {
            ping.setDescriptionComponent(API.getBaseComponent("§4a.nerotv.live §8- §cBitte aktualisiere dein Spiel!\n§cWir unterstützen nur Versionen ab der " + API.lowestVersion + "!"));
            ping.setVersion(new ServerPing.Protocol("§c" + API.lowestVersion + "-" + API.highestVersion, 1));
            vers.setName("§4NeroFall " + BungeeBase.getVersion());
        } else if(Protocol > API.highestProtocol) {
            ping.setDescriptionComponent(API.getBaseComponent("§4a.nerotv.live §8- §cDein Spiel ist zu aktuell! Wir\n§cunterstützen momentan Versionen bis zur " + API.highestVersion + "!"));
            ping.setVersion(new ServerPing.Protocol("§c" + API.lowestVersion + "-" + API.highestVersion, 1));
            vers.setName("§4NeroFall " + BungeeBase.getVersion());
        } else if(API.maintenance) {
            ping.setDescriptionComponent(API.getBaseComponent("§4a.nerotv.live §8- §cWir sind in Wartungsarbeiten!\n§cVersuche es später erneut! §4[§c"+API.lowestVersion+"§4-§c"+API.highestVersion+"§4]"));
            ping.setVersion(new ServerPing.Protocol("§cWartungsarbeiten",1));
            vers.setName("§4NeroFall " + BungeeBase.getVersion());
        } else {
            vers.setName("§9NeroFall " + BungeeBase.getVersion());
            players.setSample(new ServerPing.PlayerInfo[]{
                    new ServerPing.PlayerInfo("§9NeroFall "+BungeeBase.getVersion(), "NeroFall "+BungeeBase.getVersion())
            });
            String ip = "§x§1§8§F§F§3§1a§x§1§E§F§F§3§5.§x§2§4§F§F§3§An§x§2§A§F§F§3§Ee§x§3§0§F§F§4§2r§x§3§6§F§F§4§6o§x§3§C§F§F§4§Bt§x§4§2§F§F§4§Fv§x§4§8§F§F§5§3.§x§4§E§F§F§5§7l§x§5§4§F§F§5§Ci§x§5§A§F§F§6§0v§x§6§0§F§F§6§4e";

            ping.setDescription(ip+" §r§8» §fMinecraft§8,§7 aber §fmehr§8! §8« §e1§8.§e20§8.§eX§r\n"+ip+"§r §8» §7"+API.motd);
        }
    }

    /*@EventHandler
    public void on(ProxyDefineCommandsEvent e) {
        e.getCommands().clear();
    }*/
}