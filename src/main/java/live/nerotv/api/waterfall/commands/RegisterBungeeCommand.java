package live.nerotv.api.waterfall.commands;

import live.nerotv.api.utils.storage.types.MySQL;
import live.nerotv.api.waterfall.BungeeNero;
import live.nerotv.api.waterfall.server.BungeeServer;
import live.nerotv.api.utils.Strings;
import live.nerotv.api.waterfall.utils.communication.Communicate;
import live.nerotv.api.waterfall.utils.user.Sound;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterBungeeCommand extends Command {

    public RegisterBungeeCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if(s.hasPermission("nero.leading.register")) {
            BungeeServer bungeeServer = BungeeNero.getNeroServer();
            if(bungeeServer.isRegistered()) {
                s.sendMessage("§cDer Server ist bereits registriert§8!");
                if(s instanceof ProxiedPlayer) {
                    ProxiedPlayer p = (ProxiedPlayer)s;
                    Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                }
            } else {
                if(args.length == 1) {
                    String name = args[0];
                    int ID = bungeeServer.getServerID();
                    if(bungeeServer.getConfig().getCFG().getBoolean("MySQL.enable")) {
                        MySQL sql = bungeeServer.getSQL();
                        try {
                            sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS serverlist (ID INT,NAME VARCHAR(32))").executeUpdate();
                            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO serverlist (ID,NAME) VALUES (?,?)");
                            ps.setInt(1,ID);
                            ps.setString(2,name);
                            ps.executeUpdate();
                        } catch (SQLException e) {
                            s.sendMessage("§cDer Server ist nicht mit einer Datenbank verbunden§8!");
                            if(s instanceof ProxiedPlayer) {
                                ProxiedPlayer p = (ProxiedPlayer)s;
                                Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                            }
                        }
                        bungeeServer.getConfig().getCFG().set("Server.ID", ID);
                        bungeeServer.getConfig().saveConfig();
                        bungeeServer.getConfig().reloadConfig();
                        bungeeServer.setRegistered(true);
                        s.sendMessage(Strings.prefix() + "§7Du hast den Server erfolgreich unter der ID §e" + ID + "§7 registriert§8!");
                        if(s instanceof ProxiedPlayer) {
                            ProxiedPlayer p = (ProxiedPlayer)s;
                            Communicate.sendSound(p, Sound.ENTITY_CHICKEN_EGG.toString());
                        }
                    } else {
                        s.sendMessage("§cDer Server ist nicht mit einer Datenbank verbunden§8!");
                        if(s instanceof ProxiedPlayer) {
                            ProxiedPlayer p = (ProxiedPlayer)s;
                            Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                        }
                    }
                } else {
                    s.sendMessage("§4Fehler: §c/register §c[name]");
                    if(s instanceof ProxiedPlayer) {
                        ProxiedPlayer p = (ProxiedPlayer)s;
                        Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
                    }
                }
            }
        } else {
            s.sendMessage(Strings.noPerms());
            if(s instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer)s;
                Communicate.sendSound(p, Sound.BLOCK_ANVIL_BREAK.toString());
            }
        }
    }
}
