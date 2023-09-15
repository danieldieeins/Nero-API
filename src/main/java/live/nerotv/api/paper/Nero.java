package live.nerotv.api.paper;

import live.nerotv.api.paper.coins.Coinsystem;
import live.nerotv.api.paper.coins.vaultSupport.VaultCoins;
import live.nerotv.api.paper.commands.CoinsCommand;
import live.nerotv.api.paper.commands.GetIDCommand;
import live.nerotv.api.paper.commands.RegisterCommand;
import live.nerotv.api.paper.commands.VersionCommand;
import live.nerotv.api.paper.listeners.PlayerChatListener;
import live.nerotv.api.paper.listeners.PlayerConnectListener;
import live.nerotv.api.paper.listeners.PlayerJoinListener;
import live.nerotv.api.paper.listeners.PlayerQuitListener;
import live.nerotv.api.paper.server.Server;
import live.nerotv.api.paper.utils.NeroAPI;
import live.nerotv.api.paper.utils.user.User;
import live.nerotv.api.utils.storage.types.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.UUID;

public final class Nero {

    private static Server server;
    private static Coinsystem coinsystem;
    private static NeroAPI zAPI;
    private static PluginManager pm;
    private static Preloader instance;

    public void onLoad(Preloader preloader) {
        instance = preloader;
        zAPI = new NeroAPI();
        server = new Server();
    }

    public void onEnable() {
        pm = Bukkit.getPluginManager();
        if(getNeroServer().getConfig().getCFG().getBoolean("MySQL.enable")) {
            coinsystem = new Coinsystem(getNeroServer().getSQL());
        } else {
            coinsystem = new Coinsystem(new SQLite("Nero/SQL/api.sql"));
        }
        if(pm.getPlugin("Vault")!=null) {
            VaultCoins.setupEconomy();
        }
        initListeners();
        instance.getCommand("version").setExecutor(new VersionCommand());
        instance.getCommand("version").setTabCompleter(new VersionCommand());
        instance.getCommand("coins").setExecutor(new CoinsCommand());
        instance.getCommand("register").setExecutor(new RegisterCommand());
        instance.getCommand("getid").setExecutor(new GetIDCommand());
        server.generateID();
    }

    public void onDisable() {
        instance = null;
        coinsystem = null;
        server = null;
        zAPI = null;
        pm = null;
    }

    public static Preloader getInstance() {
        return instance;
    }

    public static Server getNeroServer() {
        return server;
    }

    public static Coinsystem getCoinsystem() {
        return coinsystem;
    }

    public static HashMap<UUID, User> getOnlineUsers() {
        return getAPI().getOnlineUsers();
    }

    public static NeroAPI getAPI() {
        return zAPI;
    }

    public static PluginManager getPluginManager() {
        return pm;
    }

    public static User getUser(UUID uuid) {
        return getAPI().getOnlineUser(uuid);
    }

    public static User getUser(Player player) {
        return getAPI().getOnlineUser(player.getUniqueId());
    }

    private void initListeners() {
        initListenerClass(new PlayerChatListener());
        initListenerClass(new PlayerConnectListener());
        initListenerClass(new PlayerJoinListener());
        initListenerClass(new PlayerQuitListener());
    }

    private void initListenerClass(Listener listener) {
        getPluginManager().registerEvents(listener,instance);
    }
}
