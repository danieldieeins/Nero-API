package live.nerotv.api.waterfall.bungeebase.api;

import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.waterfall.bungeebase.BungeeBase;
import live.nerotv.api.waterfall.utils.user.Sound;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class API {

    public static String Prefix = "§a§8.§anerotv§8.§alive §8» §7";
    public static String lowestVersion; //= ConfigAPI.CFG.getString("Settings.Version.Lowest");
    public static String highestVersion; //= ConfigAPI.CFG.getString("Settings.Version.Highest");
    public static int lowestProtocol;
    public static int highestProtocol;
    public static boolean maintenance;
    public static int RestartDay = getYearDay()+1;
    private static boolean isStopping = false;

    public static String getDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy.HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static void registerCommand(Plugin plugin, Command command, String name) {
        sendMessage("§0  §f--> §7Der Command §8\"§e"+name+"§8\"§7 wird geladen...");
        BungeeNero.getPluginManager().registerCommand(plugin,command);
    }

    public static void registerListener(Plugin plugin,Listener listener,String name) {
        sendMessage("§0  §f--> §7Der Listener §8\"§e"+name+"§8\"§7 wird geladen...");
        BungeeNero.getPluginManager().registerListener(plugin,listener);
    }

    public static void registerEvent(Plugin plugin,Listener listener,String name) {
        registerListener(plugin,listener,name);
    }

    public static String motd = "§7ladevorgang...";
    public static void initConfig() {
        ConfigAPI.reloadConfig();
        ConfigAPI.checkEntry("Strings.ServerMOTD.Line","§7ladevorgang...",ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Settings.Version.Lowest","1.19",ConfigAPI.Config,ConfigAPI.CFG);
        lowestVersion = ConfigAPI.CFG.getString("Settings.Version.Lowest");
        ConfigAPI.checkEntry("Settings.Version.Highest","1.19",ConfigAPI.Config,ConfigAPI.CFG);
        highestVersion = ConfigAPI.CFG.getString("Settings.Version.Highest");
        ConfigAPI.checkEntry("Settings.Maintenance",true,ConfigAPI.Config,ConfigAPI.CFG);
        maintenance = ConfigAPI.CFG.getBoolean("Settings.Maintenance");
        ConfigAPI.checkEntry("Settings.Version.Protocol",759,ConfigAPI.Config,ConfigAPI.CFG);
        ConfigAPI.checkEntry("Settings.Version.ProtocolEnd",759,ConfigAPI.Config,ConfigAPI.CFG);
        lowestProtocol = ConfigAPI.CFG.getInt("Settings.Version.Protocol");
        highestProtocol = ConfigAPI.CFG.getInt("Settings.Version.ProtocolEnd");
        ConfigAPI.saveConfig();
        ConfigAPI.reloadConfig();
        motd = ConfigAPI.CFG.getString("Strings.ServerMOTD.Line");
    }

    public static void setMaintenance(boolean state) {
        ConfigAPI.CFG.set("Settings.Maintenance",state);
        ConfigAPI.saveConfig(ConfigAPI.Config,ConfigAPI.CFG);
        maintenance = state;
        if(maintenance) {
            for(ProxiedPlayer all:ProxyServer.getInstance().getPlayers()) {
                if(!all.hasPermission("nero.bypassmaintenance")) {
                    all.disconnect(API.getBaseComponent("§cDer Server befindet sich nun in §4Wartungsarbeiten§c!"));
                }
            }
        }
    }

    public static void toggleMaintenance() {
        setMaintenance(!maintenance);
    }

    public static void setMotd(Integer line,String content) {
        ConfigAPI.reloadConfig();
        ConfigAPI.CFG.set("Strings.ServerMOTD.Line0"+line,content);
        ConfigAPI.saveConfig();
    }

    public static void setMotd(String lineOne,String lineTwo) {
        setMotd(1,lineOne);
        setMotd(2,lineTwo);
    }

    public static String getMotd(Integer line) {
        return ConfigAPI.CFG.getString("Strings.ServerMOTD.Line0"+line).replace("&","§");
    }

    public static void sendBanMessage(ProxiedPlayer player) {
        String BanReason = "§cRegelverstoß";
        if(ConfigAPI.getPlayerConfiguration(player).contains("Bansystem.Ban.Reason") && ConfigAPI.getPlayerConfiguration(player).getString("Bansystem.Ban.Reason")!=null && !ConfigAPI.getPlayerConfiguration(player).getString("Bansystem.Ban.Reason").equals("")) {
            BanReason = ConfigAPI.getPlayerConfiguration(player).getString("Bansystem.Ban.Reason");
        }
        String BanFormat = "§cDu kannst dem nerotvlive Netzwerk nicht beitreten§8!\n§0\n§4Grund: §c"+BanReason+"§0\n§0\n§7Solltest du zu unrecht gesperrt worden sein,\n§7melde dich bitte auf unserem Discord§8!\n§9§nhttps://discord.gg/2qv979TqsH";
        player.disconnect(getBaseComponent(BanFormat.replace("&","§")));
    }

    public static boolean isPlayerBanned(String SID) {
        SID = SID.replace("-","").replace("localhost","127.0.0.1");
        File PlayerFile = new File("plugins/BungeeBase/Players/"+SID+".yml");
        if(PlayerFile.exists()) {
            Configuration PF = ConfigAPI.loadConfiguration(PlayerFile);
            if (PF.contains("Bansystem.isBanned")) {
                if (PF.getBoolean("Bansystem.isBanned")) {
                    return true;
                } else if (PF.getString("Bansystem.isBanned").equalsIgnoreCase("true")) {
                    return true;
                } else {
                    PlayerFile.delete();
                    return false;
                }
            } else {
                PlayerFile.delete();
                return false;
            }
        } else {
            PlayerFile.delete();
            return false;
        }
    }

    public static boolean isPlayerBanned(UUID UID) {
        return isPlayerBanned(UID.toString().replace("-",""));
    }

    public static boolean isPlayerBanned(ProxiedPlayer player) {
        return isPlayerBanned(player.getUniqueId());
    }

    public static void banPlayer(String SID,String banReason) {
        SID = SID.replace("-","").replace("localhost","127.0.0.1");
        File PlayerFile = new File("plugins/BungeeBase/Players/"+SID+".yml");
        Configuration PF = ConfigAPI.loadConfiguration(PlayerFile);
        PF.set("Bansystem.isBanned",true);
        PF.set("Bansystem.Ban.Reason",banReason);
        ConfigAPI.saveConfig(PlayerFile,PF);
        if(ProxyServer.getInstance().getPlayer(SID)!=null) {
            ProxiedPlayer t = ProxyServer.getInstance().getPlayer(SID);
            sendBanMessage(t);
            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                if(all.hasPermission("nero.team.ban")) {
                    API.sendMessage(all,"Der Spieler §e"+SID+"§8/§e"+t.getName()+"§7 wurde für §f"+banReason+"§7 gebannt§8!");
                }
            }
        } else {
            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                if(all.hasPermission("nero.team.ban")) {
                    if(UUIDFetcher.getName(SID)!=null) {
                        API.sendMessage(all, "Der Spieler §e" + SID + "§8/§e"+UUIDFetcher.getName(SID)+"§7 wurde für §f" + banReason + "§7 gebannt§8!");
                    } else {
                        API.sendMessage(all, "Der Spieler §e" + SID + "§7 wurde für §f" + banReason + "§7 gebannt§8!");
                    }
                }
            }
        }
    }

    public static void banPlayer(UUID UID,String banReason) {
        banPlayer(UID.toString().replace("-",""),banReason);
        if(ProxyServer.getInstance().getPlayer(UID)!=null) {
            sendBanMessage(ProxyServer.getInstance().getPlayer(UID));
        }
    }

    public static void banPlayer(ProxiedPlayer player,String banReason) {
        banPlayer(player.getUniqueId(),banReason);
        sendBanMessage(player);
    }

    public static void unbanPlayer(String SID) {
        SID = SID.replace("-","");
        File PlayerFile = new File("plugins/BungeeBase/Players/"+SID+".yml");
        Configuration PF = ConfigAPI.loadConfiguration(PlayerFile);
        PF.set("Bansystem.Ban.Reason",null);
        PF.set("Bansystem.isBanned",false);
        ConfigAPI.saveConfig(PlayerFile,PF);
        PlayerFile.delete();
    }

    public static void unbanPlayer(UUID UID) {
        unbanPlayer(UID.toString().replace("-",""));
    }

    public static void unbanPlayer(ProxiedPlayer player) {
        unbanPlayer(player.getUniqueId());
    }

    public static void getBanList(CommandSender s) {
        File folder = new File("plugins/BungeeBase/Players");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String nameComponent = listOfFiles[i].getName().replace(".yml","");
                String name;
                if(UUIDFetcher.getName(nameComponent)!=null) {
                    name = "§a"+UUIDFetcher.getName(nameComponent)+",";
                } else {
                    name = "§a"+nameComponent+",";
                }
                s.sendMessage(API.getBaseComponent(name));
            } else if (listOfFiles[i].isDirectory()) {
                String nameComponent = listOfFiles[i].getName().replace(".yml","");
                String name;
                if(UUIDFetcher.getName(nameComponent)!=null) {
                    name = "§2"+UUIDFetcher.getName(nameComponent)+",";
                } else {
                    name = "§2"+nameComponent+",";
                }
                s.sendMessage(API.getBaseComponent("§2"+listOfFiles[i].getName().replace(".yml",",")));
            }
        }
    }

    public static void sendMessage(String message) {
        ProxyServer.getInstance().getConsole().sendMessage(getBaseComponent(Prefix+message));
    }

    public static void sendMessage(String message,Boolean prefix) {
        if(prefix) {
            ProxyServer.getInstance().getConsole().sendMessage(getBaseComponent(Prefix+message));
        } else {
            ProxyServer.getInstance().getConsole().sendMessage(getBaseComponent(message));
        }
    }

    public static void sendMessage(CommandSender receiver,String message) {
        receiver.sendMessage(getBaseComponent(Prefix+message));
        if(receiver instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)receiver;
            PlayerAPI.playNewSound(p, Sound.ENTITY_CHICKEN_EGG);
        }
    }

    public static void sendMessage(CommandSender receiver,String message,Boolean prefix) {
        if(prefix) {
            receiver.sendMessage(getBaseComponent(Prefix+message));
        } else {
            receiver.sendMessage(getBaseComponent(message));
        }
        if(receiver instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)receiver;
            PlayerAPI.playNewSound(p, Sound.ENTITY_CHICKEN_EGG);
        }
    }

    public static void sendMessage(ProxiedPlayer receiver,String message) {
        receiver.sendMessage(getBaseComponent(Prefix+message));
        PlayerAPI.playNewSound(receiver, Sound.ENTITY_CHICKEN_EGG);
    }

    public static void sendMessage(ProxiedPlayer receiver,String message,Boolean prefix) {
        if(prefix) {
            receiver.sendMessage(getBaseComponent(Prefix+message));
        } else {
            receiver.sendMessage(getBaseComponent(message));
        }
        PlayerAPI.playNewSound(receiver, Sound.ENTITY_CHICKEN_EGG);
    }

    public static void sendErrorMessage(String Message) {
        ProxyServer.getInstance().getConsole().sendMessage(getBaseComponent(Message));
    }

    public static void sendErrorMessage(CommandSender receiver,String Message) {
        receiver.sendMessage(getBaseComponent(Message));
        if(receiver instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)receiver;
            PlayerAPI.playNewSound(p, Sound.BLOCK_ANVIL_BREAK);
        }
    }

    @Deprecated
    public static void sendConsoleErrorMessage(String Message) {
        sendErrorMessage(Message);
    }

    public static void sendErrorMessage(ProxiedPlayer receiver,String Message) {
        receiver.sendMessage(getBaseComponent(Message));
        PlayerAPI.playNewSound(receiver, Sound.BLOCK_ANVIL_BREAK);
    }

    public static BaseComponent getBaseComponent(String Message) {
        return new ComponentBuilder(Message).getCurrentComponent();
    }

    public static void sendInit() {
        sendMessage("§9█§9█§9█§9█§9█§9█§9█§8╗§9█§9█§8╗§8░§8░§8░§9█§9█§8╗§9█§9█§9█§8╗§8░§8░§9█§9█§8╗§9█§9█§9█§9█§9█§9█§9█§8╗§8░§9█§9█§9█§9█§9█§8╗§8░§9█§9█§9█§8╗§8░§8░§9█§9█§8╗");
        sendMessage("§8╚§8═§8═§8═§8═§9█§9█§8║§8╚§9█§9█§8╗§8░§9█§9█§8╔§8╝§9█§9█§9█§9█§8╗§8░§9█§9█§8║§9█§9█§8╔§8═§8═§8═§8═§8╝§9█§9█§8╔§8═§8═§9█§9█§8╗§9█§9█§9█§9█§8╗§8░§9█§9█§8║");
        sendMessage("§8░§8░§9█§9█§9█§8╔§8═§8╝§8░§8╚§9█§9█§9█§9█§8╔§8╝§8░§9█§9█§8╔§9█§9█§8╗§9█§9█§8║§9█§9█§9█§9█§9█§8╗§8░§8░§9█§9█§8║§8░§8░§9█§9█§8║§9█§9█§8╔§9█§9█§8╗§9█§9█§8║");
        sendMessage("§9█§9█§8╔§8═§8═§8╝§8░§8░§8░§8░§8╚§9█§9█§8╔§8╝§8░§8░§9█§9█§8║§8╚§9█§9█§9█§9█§8║§9█§9█§8╔§8═§8═§8╝§8░§8░§9█§9█§8║§8░§8░§9█§9█§8║§9█§9█§8║§8╚§9█§9█§9█§9█§8║");
        sendMessage("§9█§9█§9█§9█§9█§9█§9█§8╗§8░§8░§8░§9█§9█§8║§8░§8░§8░§9█§9█§8║§8░§8╚§9█§9█§9█§8║§9█§9█§9█§9█§9█§9█§9█§8╗§8╚§9█§9█§9█§9█§9█§8╔§8╝§9█§9█§8║§8░§8╚§9█§9█§9█§8║");
        sendMessage("§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝§8╚§8═§8═§8═§8═§8═§8═§8╝§8░§8╚§8═§8═§8═§8═§8╝§8░§8╚§8═§8╝§8░§8░§8╚§8═§8═§8╝");
        sendMessage("§fPlugin by §cnerotvlive§f!");
    }

    public static int getYearDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static void checkForRestart() {
        BungeeBase.getInstance().getProxy().getScheduler().schedule(BungeeBase.getInstance(), () -> {
            if(!isStopping) {
                if (getYearDay() == RestartDay) {
                    BungeeNero.getNeroServer().stopNetwork();
                }
            }
        }, 0, 1, TimeUnit.MINUTES);

    }
}