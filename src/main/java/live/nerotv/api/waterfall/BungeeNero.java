package live.nerotv.api.waterfall;

import live.nerotv.api.waterfall.bungeebase.BungeeBase;
import live.nerotv.api.waterfall.commands.GetBungeeIDCommand;
import live.nerotv.api.waterfall.commands.RegisterBungeeCommand;
import live.nerotv.api.waterfall.listeners.PlayerDisconnectListener;
import live.nerotv.api.waterfall.listeners.PlayerLoginListener;
import live.nerotv.api.waterfall.server.BungeeServer;
import live.nerotv.api.waterfall.utils.BungeeNeroAPI;
import live.nerotv.api.waterfall.utils.user.BungeeUser;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.util.HashMap;
import java.util.UUID;

public class BungeeNero {

    private static BungeeServer bungeeServer;
    private static BungeeNeroAPI zAPI;
    private static PluginManager pm;
    private static Plugin instance;

    public BungeeNero(Plugin plugin) {
        instance = plugin;
    }

    public void onLoad() {
        zAPI = new BungeeNeroAPI();
        bungeeServer = new BungeeServer();
        pm = ProxyServer.getInstance().getPluginManager();
        BungeeBase.onLoad();
    }

    public void onEnable() {
        initListeners();
        pm.registerCommand(instance,new GetBungeeIDCommand("GetProxyID"));
        pm.registerCommand(instance,new RegisterBungeeCommand("RegisterProxy"));
        bungeeServer.generateID();
        BungeeBase.onEnable();
    }

    public void onDisable() {
        BungeeBase.onDisable();
        instance = null;
        bungeeServer = null;
        zAPI = null;
        pm = null;
    }

    public static Plugin getInstance() {
        return instance;
    }

    public static BungeeServer getNeroServer() {
        return bungeeServer;
    }

    public static HashMap<UUID, BungeeUser> getOnlineUsers() {
        return getAPI().getOnlineUsers();
    }

    public static BungeeNeroAPI getAPI() {
        return zAPI;
    }

    public static PluginManager getPluginManager() {
        return pm;
    }

    private void initListeners() {
        initListenerClass(new PlayerLoginListener());
        initListenerClass(new PlayerDisconnectListener());
    }

    private void initListenerClass(Listener listener) {
        pm.registerListener(instance,listener);
    }
}