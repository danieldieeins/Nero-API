package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.waterfall.bungeebase.api.API;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BSRL extends Command {

    public BSRL() {
        super("bsrl",null,"bungeestop");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.leading")) {
            API.sendMessage(s,"§7Du hast das Stopprotokoll gestartet§8!");
            BungeeNero.getNeroServer().stopNetwork();
        } else {
            API.sendErrorMessage(s, Strings.noPerms());
        }
    }
}