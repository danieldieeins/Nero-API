package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.bungeebase.api.API;
import live.nerotv.api.waterfall.bungeebase.api.UUIDFetcher;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Get extends Command {

    public Get() {
        super("Get");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.team.get")) {
            if(args.length == 0) {
                API.sendErrorMessage(s,"§4Fehler: §c/get [Name]");
            } else {
                if(UUIDFetcher.getUUID(args[0])!=null) {
                    API.sendMessage(s,"§7Die UUID von §a"+args[0]+"§7 ist§8: §e\""+UUIDFetcher.getUUID(args[0])+"\"");
                } else if(UUIDFetcher.getName(args[0])!=null) {
                    API.sendMessage(s,"§7Der Name von §a"+args[0]+"§7 ist§8: §e\""+UUIDFetcher.getName(args[0])+"\"");
                } else {
                    API.sendErrorMessage(s,"§cDer Spieler §e"+args[0]+"§c ist unbekannt.");
                }
            }
        } else {
            API.sendErrorMessage(s, Strings.noPerms());
        }
    }
}
