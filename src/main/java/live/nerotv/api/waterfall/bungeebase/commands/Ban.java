package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.bungeebase.api.API;
import live.nerotv.api.waterfall.bungeebase.api.BanAPI;
import live.nerotv.api.waterfall.bungeebase.api.UUIDFetcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class Ban extends Command {

    public Ban() {
        super("Ban");
    }

    private void sendSyntax(CommandSender s) {
        API.sendErrorMessage(s,"§4Fehler: §c/ban [Spieler/UUID] §7[Grund]");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.team.ban")) {
            if(args.length == 0) {
                sendSyntax(s);
            } else {
                String r;
                if(args.length == 1) {
                    r = "§cRegelverstoß ";
                } else {
                    r = "";
                    for(int i=1;i<args.length;i++) {
                        r=r+args[i]+" ";
                    }
                }
                if(ProxyServer.getInstance().getPlayer(args[0])!=null) {
                    if(BanAPI.isBanned(ProxyServer.getInstance().getPlayer(args[0]).getUniqueId())) {
                        API.sendErrorMessage(s,"§cDieser Spieler ist bereits gebannt§8!");
                        return;
                    }
                    BanAPI.ban(ProxyServer.getInstance().getPlayer(args[0]),r);
                    API.sendMessage(s,"§7Du hast den Spieler §e"+args[0]+"§7 wegen §c"+r+"§7gebannt§8.",true);
                    String name = "der Konsole";
                    if(s instanceof ProxiedPlayer) {
                        ProxiedPlayer p = (ProxiedPlayer)s;
                        name = p.getName();
                    }
                    for(ProxiedPlayer all:ProxyServer.getInstance().getPlayers()) {
                        if(all.hasPermission("nero.team.ban")) {
                            if(all.getName()!=name) {
                                API.sendMessage(all, "§7Der Spieler §e" + args[0] + "§7 wurde von §a" + name + "§7 für §c" + r + "§7gebannt.", true);
                            }
                        }
                    }
                    if(name!="der Konsole") {
                        API.sendMessage("§7Der Spieler §e" + args[0] + "§7 wurde von §a" + name + "§7 für §c" + r + "§7gebannt.", true);
                    }
                } else {
                    UUID uuid;
                    try {
                        uuid = UUID.fromString(UUIDFetcher.getUUID(args[0]).replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
                    } catch (Exception e) {
                        API.sendErrorMessage(s,Strings.playerNotFound());
                        return;
                    }
                    if(BanAPI.isBanned(uuid)) {
                        API.sendErrorMessage(s,"§cDieser Spieler ist bereits gebannt§8!");
                        return;
                    }
                    try {
                        BanAPI.ban(uuid, r);
                    } catch (Exception e) {
                        API.sendErrorMessage(s,"§cEs ist ein unbekannter Fehler aufgetreten§8.§c Der Spieler ist §nwahrscheinlich§c nicht gebannt§8. §cMache /banlist§8, §cum dies zu überprüfen§8.");
                        return;
                    }
                    API.sendMessage(s,"§7Du hast den Spieler §e"+args[0]+"§7 wegen §c"+r+"§7gebannt§8.",true);
                    String name = "der Konsole";
                    if(s instanceof ProxiedPlayer) {
                        ProxiedPlayer p = (ProxiedPlayer)s;
                        name = p.getName();
                    }
                    for(ProxiedPlayer all:ProxyServer.getInstance().getPlayers()) {
                        if(all.hasPermission("nero.team.ban")) {
                            if(all.getName()!=name) {
                                API.sendMessage(all, "§7Der Spieler §e" + args[0] + "§7 wurde von §a" + name + "§7 für §c" + r + "§7gebannt.", true);
                            }
                        }
                    }
                    if(name!="der Konsole") {
                        API.sendMessage("§7Der Spieler §e" + args[0] + "§7 wurde von §a" + name + "§7 für §c" + r + "§7gebannt.", true);
                    }
                }
            }
        } else {
            API.sendErrorMessage(s, Strings.noPerms());
        }
    }
}