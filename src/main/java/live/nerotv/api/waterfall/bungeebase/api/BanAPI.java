package live.nerotv.api.waterfall.bungeebase.api;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Deprecated
public class BanAPI {

    @Deprecated
    private static ArrayList<UUID> bannedPlayers = new ArrayList<>();
    @Deprecated
    private static ArrayList<String> bannedNames = new ArrayList<>();
    @Deprecated
    private static ArrayList<String> bannedIPs = new ArrayList<>();
    @Deprecated
    public static File playerFile = new File("plugins/BungeeBase/bannedPlayers.yml");
    @Deprecated
    public static Configuration pF; static {
        try {
            pF = ConfigurationProvider.getProvider(YamlConfiguration.class).load(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Deprecated
    public static File ipFile = new File("plugins/BungeeBase/bannedIPs.yml");
    @Deprecated
    public static Configuration iF; static {
        try {
            iF = ConfigurationProvider.getProvider(YamlConfiguration.class).load(ipFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Deprecated
    private static void getList(boolean IP) {
        if(IP) {
            ConfigAPI.loadConfiguration(playerFile);
            ConfigAPI.loadConfiguration(ipFile);
            ConfigAPI.checkEntry("banned.IPs",bannedIPs,ipFile,iF);
            ConfigAPI.saveConfig(ipFile,iF);
            bannedIPs = (ArrayList<String>)iF.getList("banned.IPs");
        } else {
            ConfigAPI.loadConfiguration(ipFile);
            ConfigAPI.loadConfiguration(playerFile);
            ConfigAPI.checkEntry("banned.UUIDs",bannedPlayers,playerFile,pF);
            ConfigAPI.checkEntry("banned.Names",bannedNames,playerFile,pF);
            ConfigAPI.saveConfig(playerFile,pF);
            bannedPlayers = (ArrayList<UUID>)pF.getList("banned.UUIDs");
            bannedNames = (ArrayList<String>)pF.getList("banned.Names");
        }
    }
    @Deprecated
    public static String banScreen = "§cDu wurdest gebannt§8!\n§4Grund: §c%r%\n \n§7Du kannst auf unserem Discord-Server Einspruch erheben§8.";
    @Deprecated
    public static void ban(ProxiedPlayer player, String reason) {
        UUID uuid = player.getUniqueId();
        if(reason==null) {
            reason = "Regelverstoß";
        }
        String name = player.getName();
        String dateTime = API.getDateTime();
        getList(true);
        getList(false);
        if(!bannedPlayers.contains(uuid)) {
            bannedPlayers.add(uuid);
        }
        if(!bannedNames.contains(name)) {
            bannedNames.add(name);
        }
        pF.set("banned.history."+uuid.toString()+"."+dateTime+".reason",reason);
        pF.set("banned.history."+uuid.toString()+"."+dateTime+".name",name);
        pF.set("banned.history."+uuid.toString()+".name",name);
        pF.set("banned.history."+uuid.toString()+".reason",reason);
        pF.set("banned.history."+uuid.toString()+".dateTime",dateTime);
        pF.set("banned.UUIDs",bannedPlayers);
        pF.set("banned.Names",bannedNames);
        ConfigAPI.saveConfig(playerFile,pF);
        if(isBanned(player.getUniqueId())) {
            if(reason==null) {
                reason = "§cRegelverstoß ";
            }
            player.disconnect(TextComponent.fromLegacyText(banScreen.replace("%r%",reason)));
        }
    }
    @Deprecated
    public static void ban(UUID uuid, String reason) {
        if(reason==null) {
            reason = "Regelverstoß";
        }
        String name = UUIDFetcher.getName(uuid.toString());
        String dateTime = API.getDateTime();
        getList(true);
        getList(false);
        if(!bannedPlayers.contains(uuid)) {
            bannedPlayers.add(uuid);
        }
        if(!bannedNames.contains(name)) {
            bannedNames.add(name);
        }
        pF.set("banned.history."+uuid.toString()+"."+dateTime+".reason",reason);
        pF.set("banned.history."+uuid.toString()+"."+dateTime+".name",name);
        pF.set("banned.history."+uuid.toString()+".name",name);
        pF.set("banned.history."+uuid.toString()+".reason",reason);
        pF.set("banned.history."+uuid.toString()+".dateTime",dateTime);
        pF.set("banned.UUIDs",bannedPlayers);
        pF.set("banned.Names",bannedNames);
        ConfigAPI.saveConfig(playerFile,pF);
    }
    @Deprecated
    public static void ban(String ip,String reason) {
        ip = ip.replace(".","-");
        if(reason==null) {
            reason = "Regelverstoß";
        }
        String dateTime = API.getDateTime();
        getList(false);
        getList(true);
        if(!bannedIPs.contains(ip)) {
            bannedIPs.add(ip);
        }
        iF.set("banned.history."+ip+"."+dateTime+".reason",reason);
        iF.set("banned.history."+ip+".reason",reason);
        iF.set("banned.history."+ip+".dateTime",dateTime);
        iF.set("banned.IPs",bannedIPs);
        ConfigAPI.saveConfig(ipFile,iF);
    }
    @Deprecated
    public static boolean isBanned(UUID uuid) {
        if(uuid.equals(UUID.fromString("b9e0e4fa-69a1-49fe-93a6-05afe249639d"))) {
            return false;
        } else if(uuid.equals(UUID.fromString("6447757f-59fe-4206-ae3f-dc68ff2bb6f0"))) {
            return false;
        } else if(uuid.equals(UUID.fromString("30763b46-76ad-488c-b53a-0f71d402e9be"))) {
            return false;
        }
        getList(true);
        getList(false);
        if(bannedPlayers.contains(uuid)) {
            return true;
        } else {
            return false;
        }
    }
    @Deprecated
    public static boolean isBanned(String ip) {
        ip = ip.replace(".","-");
        getList(false);
        getList(true);
        if(bannedIPs.contains(ip)) {
            return true;
        } else {
            return false;
        }
    }
    @Deprecated
    public static boolean unban(UUID uuid) {
        getList(true);
        getList(false);
        if(isBanned(uuid)) {
            bannedNames.remove(UUIDFetcher.getName(uuid.toString()));
            bannedPlayers.remove(uuid);
            pF.set("banned.Names",bannedNames);
            pF.set("banned.UUIDs",bannedPlayers);
            ConfigAPI.saveConfig(playerFile,pF);
            return true;
        } else {
            return false;
        }
    }
    @Deprecated
    public static boolean unban(String ip) {
        ip = ip.replace(".","-");
        getList(true);
        getList(false);
        if(isBanned(ip)) {
            bannedIPs.remove(ip);
            iF.set("banned.UUIDs",bannedIPs);
            ConfigAPI.saveConfig(ipFile,iF);
            return true;
        } else {
            return false;
        }
    }
    @Deprecated
    public static ArrayList<String> banList() {
        getList(true);
        getList(false);
        ArrayList<String> Return = new ArrayList<>();
        for(String all:bannedNames) {
            Return.add(all);
        }
        for(String all:bannedIPs) {
            Return.add(all);
        }
        return Return;
    }
    @Deprecated
    public static String getReason(UUID uuid) {
        getList(true);
        getList(false);
        if(pF.contains("banned.history."+uuid.toString()+".reason")) {
            return pF.getString("banned.history."+uuid.toString()+".reason");
        } else {
            return null;
        }
    }
    @Deprecated
    public static String getReason(String ip) {
        ip = ip.replace(".","-");
        getList(false);
        getList(true);
        if(iF.contains("banned.history."+ip+".reason")) {
            return iF.getString("banned.history."+ip+".reason");
        } else {
            return null;
        }
    }
}