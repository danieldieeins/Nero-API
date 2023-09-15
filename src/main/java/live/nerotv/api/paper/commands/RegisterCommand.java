package live.nerotv.api.paper.commands;

import live.nerotv.api.paper.Nero;
import live.nerotv.api.paper.server.Server;
import live.nerotv.api.utils.Strings;
import live.nerotv.api.utils.storage.types.MySQL;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(s.hasPermission("nero.leading.register")) {
            Server server = Nero.getNeroServer();
            if(server.isRegistered()) {
                s.sendMessage("§cDer Server ist bereits registriert§8!");
                if(s instanceof Player) {
                    Player p = (Player)s;
                    p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                }
            } else {
                if(args.length == 1) {
                    String name = args[0];
                    int ID = server.getServerID();
                    if(server.getConfig().getCFG().getBoolean("MySQL.enable")) {
                        MySQL sql = server.getSQL();
                        try {
                            sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS serverlist (ID INT,NAME VARCHAR(32))").executeUpdate();
                            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO serverlist (ID,NAME) VALUES (?,?)");
                            ps.setInt(1,ID);
                            ps.setString(2,name);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            s.sendMessage("§cDer Server ist nicht mit einer Datenbank verbunden§8!");
                            if(s instanceof Player) {
                                Player p = (Player)s;
                                p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                            }
                        }
                        server.getConfig().getCFG().set("Server.ID", ID);
                        server.getConfig().saveConfig();
                        server.getConfig().reloadConfig();
                        server.setRegistered(true);
                        s.sendMessage(Strings.prefix() + "§7Du hast den Server erfolgreich unter der ID §e" + ID + "§7 registriert§8!");
                        if(s instanceof Player) {
                            Player p = (Player)s;
                            p.playSound(p.getLocation(),Sound.ENTITY_CHICKEN_EGG,100,100);
                        }
                    } else {
                        s.sendMessage("§cDer Server ist nicht mit einer Datenbank verbunden§8!");
                        if(s instanceof Player) {
                            Player p = (Player)s;
                            p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                        }
                    }
                } else {
                    s.sendMessage("§4Fehler: §c/register §c[name]");
                    if(s instanceof Player) {
                        Player p = (Player)s;
                        p.playSound(p.getLocation(),Sound.BLOCK_ANVIL_BREAK,100,100);
                    }
                }
            }
        } else {
            
        }
        return false;
    }
}