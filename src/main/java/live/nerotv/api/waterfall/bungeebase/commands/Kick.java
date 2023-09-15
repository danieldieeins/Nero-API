package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.bungeebase.api.API;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Kick extends Command {

    public Kick() {
        super("Kick");
    }

    private void sendSyntax(CommandSender s) {
        API.sendErrorMessage(s,"§4Fehler: §c/kick [Spieler] §7[Grund]");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.team.kick")) {
            if(args.length == 0) {
                sendSyntax(s);
            } else {
                if(ProxyServer.getInstance().getPlayer(args[0])!=null) {
                    ProxiedPlayer t = ProxyServer.getInstance().getPlayer(args[0]);
                    if (args.length == 1) {
                        t.disconnect(API.getBaseComponent("§cDu wurdest gekickt§8."));
                    } else {
                        String r = "";
                        for (int i = 1; i < args.length; i++) {
                            r = r + args[i] + " ";
                        }
                        r = r.substring(0, r.length() - 1);
                        r = r.replace("&","§");
                        t.disconnect(API.getBaseComponent("§cDu wurdest gekickt§8.\n§4Grund: §c"+r));
                    }
                } else {
                    API.sendErrorMessage(s, Strings.playerNotFound());
                }
            }
        } else {
            API.sendErrorMessage(s,Strings.noPerms());
        }
    }
}