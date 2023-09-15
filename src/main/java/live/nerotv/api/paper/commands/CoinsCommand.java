package live.nerotv.api.paper.commands;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.paper.utils.user.User;
import live.nerotv.api.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CoinsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
        if(s instanceof Player p) {
            User u = Nero.getAPI().getOnlineUser(p.getUniqueId());
            if(args.length == 0) {
                u.sendMessage("§7Du hast §a"+ Nero.getCoinsystem().getCoins(p.getUniqueId())+"§7 Coins§8!");
            } else if(args.length == 1) {
                u.sendMessage("§e"+args[0]+"§7 hat §a"+ Nero.getCoinsystem().getCoins(Bukkit.getOfflinePlayer(args[0]).getUniqueId())+"§7 Coins§8.");
            } else {
                if(p.hasPermission("komvilcraft.coinsystem.commands.coins.other")) {
                    if(args.length == 2) {
                        u.sendErrorMessage("§4Fehler§8: §c/coinsystem §7[§fSpieler§7/add/remove/set] [Spieler] [Coins]");
                    } else {
                        if(Nero.getAPI().isNumeric(args[2])) {
                            UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                            if(args[0].equalsIgnoreCase("add")) {
                                Nero.getCoinsystem().addCoins(uuid,Integer.parseInt(args[2]));
                                u.sendMessage("§7Der Spieler §e"+args[1]+"§7 hat nun §a"+ Nero.getCoinsystem().getCoins(uuid)+"§7 Coins§8!");
                            } else if(args[0].equalsIgnoreCase("rem")||args[0].equalsIgnoreCase("remove")) {
                                Nero.getCoinsystem().removeCoins(uuid,Integer.parseInt(args[2]));
                                u.sendMessage("§7Der Spieler §e"+args[1]+"§7 hat nun §a"+ Nero.getCoinsystem().getCoins(uuid)+"§7 Coins§8!");
                            } else if(args[0].equalsIgnoreCase("set")) {
                                Nero.getCoinsystem().setCoins(uuid,Integer.parseInt(args[2]));
                                u.sendMessage("§7Der Spieler §e"+args[1]+"§7 hat nun §a"+ Nero.getCoinsystem().getCoins(uuid)+"§7 Coins§8!");
                            } else {
                                u.sendErrorMessage("§4Fehler§8: §c/coinsystem §7[§fSpieler§7/add/remove/set] [Spieler] [Coins]");
                            }
                        } else {
                            u.sendErrorMessage("§4Fehler§8: §c"+args[2]+" ist keine gültige Zahl§8!");
                        }
                    }
                } else {
                    u.sendErrorMessage(Strings.noPerms());
                }
            }
        } else {
            if(s.hasPermission("komvilcraft.coinsystem.commands.coins.other")) {
                if (args.length == 1) {
                    s.sendMessage(Strings.prefix()+"§e"+args[0]+"§7 hat §a"+ Nero.getCoinsystem().getCoins(Bukkit.getOfflinePlayer(args[0]).getUniqueId())+"§7 Coins§8.");
                } else if (args.length == 3) {
                    if(Nero.getAPI().isNumeric(args[2])) {
                        UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                        if(args[0].equalsIgnoreCase("add")) {
                            Nero.getCoinsystem().addCoins(uuid,Integer.parseInt(args[2]));
                            s.sendMessage(Strings.prefix()+"§7Der Spieler §e"+args[1]+"§7 hat nun §a"+ Nero.getCoinsystem().getCoins(uuid)+"§7 Coins§8!");
                        } else if(args[0].equalsIgnoreCase("rem")||args[0].equalsIgnoreCase("remove")) {
                            Nero.getCoinsystem().removeCoins(uuid,Integer.parseInt(args[2]));
                            s.sendMessage(Strings.prefix()+"§7Der Spieler §e"+args[1]+"§7 hat nun §a"+ Nero.getCoinsystem().getCoins(uuid)+"§7 Coins§8!");
                        } else if(args[0].equalsIgnoreCase("set")) {
                            Nero.getCoinsystem().setCoins(uuid,Integer.parseInt(args[2]));
                            s.sendMessage(Strings.prefix()+"§7Der Spieler §e"+args[1]+"§7 hat nun §a"+ Nero.getCoinsystem().getCoins(uuid)+"§7 Coins§8!");
                        } else {
                            s.sendMessage("§4Fehler§8: §c/coinsystem §c[§fSpieler§c/add/remove/set] [Spieler] [Coins]");
                        }
                    } else {
                        s.sendMessage("§4Fehler§8: §c"+args[2]+" ist keine gültige Zahl§8!");
                    }
                } else {
                    s.sendMessage("§4Fehler§8: §c/coinsystem §c[§fSpieler§c/add/remove/set] [Spieler] [Coins]");
                }
            } else {
                s.sendMessage(Strings.noPerms());
            }
        }
        return false;
    }
}