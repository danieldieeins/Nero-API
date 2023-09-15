package live.nerotv.api.waterfall.utils.user;

import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.bungeebase.api.BanAPI;
import live.nerotv.api.waterfall.utils.communication.Communicate;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeeUser {

    private ProxiedPlayer player;
    private int init;
    private UUID uuid;
    private boolean isBedrock;

    public BungeeUser(UUID uuid) {
        if(ProxyServer.getInstance().getPlayer(uuid)!=null) {
            this.player = ProxyServer.getInstance().getPlayer(uuid);
        } else {
            this.player = null;
        }
        this.init = 0;
        this.uuid = uuid;
        this.isBedrock = false;
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    public int getInit() {
        return this.init;
    }

    public boolean isBedrock() {
        return this.isBedrock;
    }

    public boolean isBanned() {
        if(player!=null) {
            String a = player.getAddress().getHostString();
            if(BanAPI.isBanned(a)) {
                return true;
            }
        }
        return BanAPI.isBanned(uuid);
    }

    public void switchServer(String serverName) {
        if(this.player!=null) {
            this.player.connect(ProxyServer.getInstance().getServerInfo(serverName));
        }
    }

    public void sendRawMessage(String message) {
        if(this.player!=null) {
            this.player.sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        sendRawMessage(Strings.prefix()+message);
        if(this.player!=null) {
            Communicate.sendSound(this.player,Sound.ENTITY_CHICKEN_EGG.toString());
        }
    }

    public void sendWarnMessage(String message) {
        sendRawMessage("§e"+message);
        if(this.player!=null) {
            Communicate.sendSound(this.player,Sound.BLOCK_NOTE_BLOCK_PLING.toString());
        }
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c"+message);
        if(this.player!=null) {
            Communicate.sendSound(this.player,Sound.BLOCK_ANVIL_BREAK.toString());
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public void disconnect() {
        if(this.player!=null) {
            this.player.disconnect("Verbindung getrennt.");
            this.player = null;
        }
        this.isBedrock = false;
        this.init = -1;
        this.uuid = null;
        System.gc();
    }

    public void disconnect(String reason) {
        if(this.player!=null) {
            this.player.disconnect(reason);
            this.player = null;
        }
        this.disconnect();
    }
}
