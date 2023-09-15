package live.nerotv.api.cloud;

import live.nerotv.api.cloud.servers.ServerManager;
import live.nerotv.api.cloud.servers.ServerType;
import live.nerotv.api.waterfall.BungeeNero;
import net.md_5.bungee.api.plugin.Plugin;

public class Cloudsystem extends Plugin {

    private BungeeNero api;

    @Override
    public void onLoad() {
        ServerManager.createServer(ServerType.Lobby,"Lobby-1");

        api = new BungeeNero(this);
        api.onLoad();
    }

    @Override
    public void onEnable() {


        api.onEnable();
    }

    @Override
    public void onDisable() {


        api.onDisable();
    }
}