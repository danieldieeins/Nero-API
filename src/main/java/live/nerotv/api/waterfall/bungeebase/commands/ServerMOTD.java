package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.bungeebase.api.API;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ServerMOTD extends Command {

    public ServerMOTD() {
        super("ServerMOTD",null,"motd");
    }

    private void sendSyntax(CommandSender s) {
        API.sendErrorMessage(s,"§4Fehler: §c/motd [1/2] [Inhalt]");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.leading.servermotd")) {
            API.sendErrorMessage(s,"§4Fehler: §cDieses Feature ist zurzeit deaktiviert§8.");
        } else {
            API.sendErrorMessage(s, Strings.noPerms());
        }
    }
}
