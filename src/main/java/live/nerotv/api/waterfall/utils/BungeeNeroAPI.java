package live.nerotv.api.waterfall.utils;

import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.utils.user.BungeeUser;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BungeeNeroAPI {

    private HashMap<UUID, BungeeUser> onlineUsers;
    public BungeeNeroAPI() {
        onlineUsers = new HashMap<>();
    }

    public HashMap<UUID, BungeeUser> getOnlineUsers() {
        return this.onlineUsers;
    }

    public void connectUser(UUID uuid) {
        this.onlineUsers.put(uuid,new BungeeUser(uuid));
    }

    public void disconnectUser(BungeeUser user) {
        onlineUsers.remove(user.getUUID());
        user.disconnect();
    }

    public BungeeUser getOnlineUser(UUID uuid) {
        if(onlineUsers.containsKey(uuid)) {
            return onlineUsers.get(uuid);
        } else {
            this.connectUser(uuid);
            return this.getOnlineUser(uuid);
        }
    }

    public ArrayList<Integer> getIDS() {
        if (BungeeNero.getNeroServer().getConfig().getCFG().getBoolean("MySQL.enable")) {
            try {
                ArrayList<Integer> list = new ArrayList<>();
                PreparedStatement ps = BungeeNero.getNeroServer().getSQL().getConnection().prepareStatement("SELECT * FROM serverlist ORDER BY ID DESC");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(rs.getInt(1));
                }
                return list;
            } catch (SQLException e) {
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public int getYearDay() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public String getTime() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return format.format(now);
    }

    public int getSeconds() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("ss");
        return Integer.parseInt(format.format(now));
    }

    public int getMinute() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("mm");
        return Integer.parseInt(format.format(now));
    }

    public int getHour() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return Integer.parseInt(format.format(now));
    }

    public int getDay() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return Integer.parseInt(format.format(now).replace("0",""));
    }

    public int getMonth() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM");
        return Integer.parseInt(format.format(now).replace("0",""));
    }

    public int getYear() {
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return Integer.parseInt(format.format(now));
    }

    public static void initListenerClass(PluginManager pluginManager, Listener listener, Plugin plugin) {
        System.out.println(Strings.prefix()+"§f  -> §7Lade Listenerklasse §e"+listener.getClass().getSimpleName()+"§8...");
        pluginManager.registerListener(plugin,listener);
    }
}