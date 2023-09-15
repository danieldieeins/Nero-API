package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.bungeebase.api.API;
import live.nerotv.api.waterfall.bungeebase.api.BanAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Banlist extends Command {

    public Banlist() {
        super("Banlist",null,"blist");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.team.banlist")) {
            API.sendMessage(s,"Hier eine Liste der gebannten Spieler§8: §e"+ BanAPI.banList().toString().replaceAll("[\\[.\\]]", ""),true);
        } else {
            API.sendErrorMessage(s, Strings.noPerms());
        }
    }
}
