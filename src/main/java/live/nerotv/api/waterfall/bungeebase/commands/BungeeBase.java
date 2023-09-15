package live.nerotv.api.waterfall.bungeebase.commands;

import live.nerotv.api.waterfall.bungeebase.api.API;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BungeeBase extends Command {

    public BungeeBase() {
        super("BungeeBase",null,"author","autor","plugin","dev","developer");
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        s.sendMessage(API.getBaseComponent(API.Prefix+"§0"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9ProjectsBase§7,"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9BungeeBase§7,"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9Creative§7,"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9FightJump§7,"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9NeroAPI§7,"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9Lobbysystem3§7,"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9Stickfight §8(KnockIT)§7 und"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9SkyBlock"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§0"));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§0  §7Network coded by §cnerotvlive§8."));
        s.sendMessage(API.getBaseComponent(API.Prefix+"§f-> §9§nhttps://linktr.ee/nerotvlive"));
        API.sendMessage(s,"§0");
    }
}
