package live.nerotv.api.paper.coins;

import live.nerotv.api.paper.events.CoinChangeEvent;
import live.nerotv.api.paper.events.PostCoinChangeEvent;
import live.nerotv.api.utils.storage.types.MySQL;
import live.nerotv.api.utils.storage.types.SQLite;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Coinsystem {

    Connection con;

    public Coinsystem(MySQL mySQL) {
        con = mySQL.getConnection();
    }

    public Coinsystem(SQLite sql) {
        con = sql.getConnection();
    }

    public void checkTable() {
        try {
            PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS ecodatabase (UUID VARCHAR(100),Eco DOUBLE(30,2))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasCoins(UUID uuid) {
        checkTable();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getCoins(UUID uuid) {
        checkTable();
        double data;
        if (hasCoins(uuid)) {
            try {
                PreparedStatement ps = con.prepareStatement("SELECT Eco FROM ecodatabase WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    data = rs.getDouble("Eco");
                } else {
                    data = 0.0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                data = 0.0;
            }
        } else {
            data = 0.0;
        }
        return (int)data;
    }

    public boolean setCoins(UUID uuid, int amount) {
        int oldAmount = getCoins(uuid);
        CoinChangeEvent event = new CoinChangeEvent(uuid,oldAmount,amount,ChangeType.SET);
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            amount = event.getNewAmount();
            String SID = uuid.toString();
            checkTable();
            if (amount < 0.0D) {
                return false;
            } else {
                try {
                    if (hasCoins(uuid)) {
                        PreparedStatement ps = con.prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                        ps.setString(1, SID);
                        ps.executeUpdate();
                    }
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ecodatabase (UUID,Eco) VALUES (?,?)");
                    ps.setString(1, SID);
                    ps.setDouble(2, amount);
                    ps.executeUpdate();
                    Bukkit.getPluginManager().callEvent(new PostCoinChangeEvent(uuid,ChangeType.SET));
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public boolean addCoins(UUID uuid, int coins) {
        int oldAmount = getCoins(uuid);
        int amount = oldAmount+coins;
        CoinChangeEvent event = new CoinChangeEvent(uuid,oldAmount,amount,ChangeType.ADD);
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            amount = event.getNewAmount();
            String SID = uuid.toString();
            checkTable();
            if (amount < 0.0D) {
                return false;
            } else {
                try {
                    if (hasCoins(uuid)) {
                        PreparedStatement ps = con.prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                        ps.setString(1, SID);
                        ps.executeUpdate();
                    }
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ecodatabase (UUID,Eco) VALUES (?,?)");
                    ps.setString(1, SID);
                    ps.setDouble(2, amount);
                    ps.executeUpdate();
                    Bukkit.getPluginManager().callEvent(new PostCoinChangeEvent(uuid,ChangeType.ADD));
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public boolean removeCoins(UUID uuid, int coins) {
        int oldAmount = getCoins(uuid);
        int amount = oldAmount-coins;
        CoinChangeEvent event = new CoinChangeEvent(uuid,oldAmount,amount,ChangeType.REMOVE);
        Bukkit.getPluginManager().callEvent(event);
        if(!event.isCancelled()) {
            amount = event.getNewAmount();
            String SID = uuid.toString();
            checkTable();
            if (amount < 0.0D) {
                return false;
            } else {
                try {
                    if (hasCoins(uuid)) {
                        PreparedStatement ps = con.prepareStatement("DELETE FROM ecodatabase WHERE UUID = ?");
                        ps.setString(1, SID);
                        ps.executeUpdate();
                    }
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ecodatabase (UUID,Eco) VALUES (?,?)");
                    ps.setString(1, SID);
                    ps.setDouble(2, amount);
                    ps.executeUpdate();
                    Bukkit.getPluginManager().callEvent(new PostCoinChangeEvent(uuid,ChangeType.REMOVE));
                    return true;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public enum ChangeType {
        ADD,
        REMOVE,
        SET
    }
}