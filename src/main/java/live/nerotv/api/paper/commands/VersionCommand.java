package live.nerotv.api.paper.commands;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.utils.VersionChecker;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VersionCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("update")) {
                if(s.hasPermission("nero.admin.update")) {
                    s.sendMessage("§7Trying to start NeroAPI Update...");
                    Nero.getInstance().updater.downloadData();
                    s.sendMessage("§7Version§8: §f"+VersionChecker.version+"§8 ("+VersionChecker.newestVersion+")");
                }
            } else if(args[0].equalsIgnoreCase("version")||args[0].equalsIgnoreCase("versions")||args[0].equalsIgnoreCase("versionen")) {
                s.sendMessage("§aNeroAPI§8: §7"+Nero.getInstance().getDescription().getVersion());
                if(Bukkit.getPluginManager().getPlugin("Lobbysystem")!=null) {
                    Plugin plugin = Bukkit.getPluginManager().getPlugin("Lobbysystem");
                    try {
                        if (plugin.getDescription().getDescription().contains("Lobbysystem by nerotvlive")) {
                            s.sendMessage("§aLobbysystem§8: §7"+plugin.getDescription().getVersion());
                        }
                    } catch (NullPointerException ignore) {}
                }
            } else {
                Bukkit.dispatchCommand(s,"api");
            }
        } else {
            s.sendMessage("§9NeroAPI§8-§9"+Nero.getInstance().getDescription().getVersion()+"§8 ("+VersionChecker.newestVersion+")");
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender r, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> completer = new ArrayList<>();
        if(args.length == 1) {
            completer.add("update");
            completer.add("version");
        }
        return completer;
    }
}