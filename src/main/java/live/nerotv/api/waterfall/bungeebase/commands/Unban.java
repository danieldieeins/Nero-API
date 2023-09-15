package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.bungeebase.api.API;
import live.nerotv.api.waterfall.bungeebase.api.BanAPI;
import live.nerotv.api.waterfall.bungeebase.api.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class Unban extends Command {

    public Unban() {
        super("Unban",null,"pardon");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.team.unban")) {
            if(args.length == 0) {
                API.sendErrorMessage(s,"§4Fehler:§c /unban [Name/IP/UUID]");
            } else {
                if(args[0].contains(".")) {
                    if(BanAPI.isBanned(args[0])) {
                        BanAPI.unban(args[0]);
                        API.sendMessage(s,"§7Du hast die IP §e"+args[0]+"§7 erfolgreich §aentbannt§8.",true);
                    } else {
                        API.sendErrorMessage(s,"§cDieser Spieler ist nicht gebannt§8!");
                    }
                } else {
                    UUID uuid;
                    try {
                        uuid = UUID.fromString(args[0]);
                    } catch (Exception e) {
                        try {
                            uuid = UUID.fromString(UUIDFetcher.getUUID(args[0]).replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
                        } catch(Exception e2) {
                            API.sendErrorMessage(s, Strings.playerNotFound());
                            return;
                        }
                    }
                    if(BanAPI.isBanned(uuid)) {
                        BanAPI.unban(uuid);
                        API.sendMessage(s,"§7Du hast den Spieler §e"+args[0]+"§7 erfolgreich §aentbannt§8.",true);
                    } else {
                        API.sendErrorMessage(s,"§cDieser Spieler ist nicht gebannt§8!");
                    }
                }
            }
        } else {
            API.sendErrorMessage(s,Strings.noPerms());
        }
    }
}
