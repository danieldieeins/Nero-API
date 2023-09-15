package live.nerotv.api.paper.utils.user;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

public class User {

    private Player player;
    private String name;
    private OfflinePlayer offlinePlayer;
    private UUID uuid;
    private boolean isBanned = false;

    public User(UUID uuid) {
        if(Bukkit.getOfflinePlayer(uuid).getName()!=null) {
            name = Bukkit.getOfflinePlayer(uuid).getName();
        } else {
            name = null;
        }
        if(Bukkit.getPlayer(uuid)!=null) {
            this.player = Bukkit.getPlayer(uuid);
        } else {
            this.player = null;
        }
        this.offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getName() {
        return this.name;
    }

    public OfflinePlayer getOfflinePlayer() {
        return this.offlinePlayer;
    }

    public int getCoins() {
        return Nero.getCoinsystem().getCoins(uuid);
    }

    public boolean hasCoins() {
        return Nero.getCoinsystem().hasCoins(uuid);
    }

    public void setCoins(int coins) {
        Nero.getCoinsystem().setCoins(uuid,coins);
    }

    public void addCoins(int coins) {
        Nero.getCoinsystem().addCoins(uuid,coins);
    }

    public void removeCoins(int coins) {
        Nero.getCoinsystem().removeCoins(uuid,coins);
    }

    public boolean isBanned() {
        return isBanned;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void switchServer(String serverName) {
        if(player!=null) {
            try {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(byteArray);
                out.writeUTF("Connect");
                out.writeUTF(serverName);
                player.sendPluginMessage(Nero.getInstance(), "BungeeCord", byteArray.toByteArray());
            } catch (Exception ignore) {
            }
        }
    }

    public void sendTitle(String title) {
        sendTitle(title,null);
    }

    public void sendTitle(String title, String subtitle) {
        sendTitle(title,subtitle,10);
    }

    public void sendTitle(String title, String subtitle, int showTime) {
        if(player!=null) {
            UserTitle.sendTitle(player,1,showTime,1,title,subtitle);
        }
    }

    public void sendRawMessage(String message) {
        if(player!=null) {
            player.sendMessage(message);
        }
    }

    public void sendMessage(String message) {
        sendRawMessage(Strings.prefix()+message);
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
        }
    }

    public void sendWarnMessage(String message) {
        sendRawMessage("§e"+message);
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,100,100);
        }
    }

    public void sendErrorMessage(String message) {
        sendRawMessage("§c"+message);
        if(player!=null) {
            player.playSound(player.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
        }
    }

    public void disconnect() {
        if(player!=null) {
            player.kickPlayer("Verbindung getrennt.");
            player = null;
        }
        offlinePlayer = null;
        name = null;
        Nero.getAPI().getOnlineUsers().remove(uuid);
        uuid = null;
        System.gc();
    }

    public void disconnect(String reason) {
        if(player!=null) {
            player.kickPlayer(reason);
            player = null;
        }
        offlinePlayer = null;
        isBanned = false;
        name = null;
        Nero.getAPI().getOnlineUsers().remove(uuid);
        uuid = null;
        System.gc();
    }
}