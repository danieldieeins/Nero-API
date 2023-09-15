package live.nerotv.api.waterfall.server;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.utils.storage.types.MySQL;
import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.waterfall.bungeebase.BungeeBase;
import live.nerotv.api.waterfall.configuration.BungeeConfig;
import live.nerotv.api.waterfall.utils.BungeeCountdown;
import live.nerotv.api.waterfall.utils.communication.Communicate;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.util.concurrent.ThreadLocalRandom;

public class BungeeServer {

    private final MySQL mysql;
    private int serverID;
    private final BungeeConfig bungeeConfig;
    private boolean isRegistered;
    private boolean isStopping;
    private final ProxyServer proxyServer;

    public BungeeServer() {
        isRegistered = false;
        isStopping = false;
        proxyServer = ProxyServer.getInstance();
        bungeeConfig = new BungeeConfig("Nero/API/server.yml");
        if(bungeeConfig.getFile().exists()) {
            if(bungeeConfig.getCFG().contains("Server.ID")) {
                isRegistered = true;
            } else {
                isRegistered = false;
            }
        }
        bungeeConfig.checkEntry("MySQL.enable",false);
        bungeeConfig.checkEntry("MySQL.host","localhost");
        bungeeConfig.checkEntry("MySQL.port","3306");
        bungeeConfig.checkEntry("MySQL.user","root");
        bungeeConfig.checkEntry("MySQL.database","nero_server");
        bungeeConfig.checkEntry("MySQL.password","password");
        bungeeConfig.saveConfig();
        bungeeConfig.reloadConfig();
        if(bungeeConfig.getCFG().getBoolean("MySQL.enable")) {
            mysql = new MySQL(bungeeConfig.getCFG().getString("MySQL.host"), bungeeConfig.getCFG().getString("MySQL.port"), bungeeConfig.getCFG().getString("MySQL.database"), bungeeConfig.getCFG().getString("MySQL.user"), bungeeConfig.getCFG().getString("MySQL.password"),false);
        } else {
            mysql = null;
        }
        if (isRegistered) {
            serverID = bungeeConfig.getCFG().getInt("Server.ID");
        }
    }

    public void generateID() {
        if(!isRegistered) {
            String SID = "13" + "" + ThreadLocalRandom.current().nextInt(1000, 9999);
            int id = Integer.parseInt(SID);
            if (bungeeConfig.getCFG().getBoolean("MySQL.enable")) {
                if (BungeeNero.getAPI().getIDS().contains(id)) {
                    generateID();
                } else {
                    serverID = id;
                }
            } else {
                serverID = id;
            }
        }
    }

    public MySQL getSQL() {
        return this.mysql;
    }

    public BungeeConfig getConfig() {
        return this.bungeeConfig;
    }

    public int getServerID() {
        return this.serverID;
    }

    public boolean isRegistered() {
        return this.isRegistered;
    }

    public ProxyServer getProxyServer() {
        return this.proxyServer;
    }

    public boolean isStopping() {
        return this.isStopping;
    }

    public void setRegistered(boolean bool) {
        this.isRegistered = bool;
    }

    public void stopServer() {
        isStopping = true;
        new BungeeCountdown(27, BungeeBase.getInstance()) {
            @Override
            public void count(int current) {
                if (current < 26) {
                    sendMessage("Proxy-Neustart in " + current + " Sekunden.");
                    if (current == 0) {
                        for(ProxiedPlayer all : BungeeBase.getInstance().getProxy().getPlayers()) {
                            all.disconnect("§cNetzwerk-Neustart\n§7Bitte warte etwas§8... Es kann ein paar Minuten dauern§8,§7 bis der Server wieder erreichbar ist§8!");
                        }
                        BungeeBase.getInstance().getProxy().stop();
                    }
                }
            }
        }.start();
    }

    public void stopNetwork() {
        Communicate.sendStop();
        stopServer();
    }

    public void sendRawMessage(String message) {
        System.out.println(message);
    }

    public void sendMessage(String message) {
        sendRawMessage(Strings.prefix()+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void sendWarnMessage(String message) {
        sendRawMessage("§e"+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c"+message.replace("&&","%and%").replace("&","§").replace("%and%","&"));
    }

    public void instantStop(String reason) {
        isStopping = true;
        for(ProxiedPlayer all : proxyServer.getPlayers()) {
            all.disconnect(reason);
        }
        proxyServer.stop(reason);
    }
}