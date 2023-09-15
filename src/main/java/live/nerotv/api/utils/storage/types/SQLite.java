package live.nerotv.api.utils.storage.types;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {

    private Connection con;
    private String path;

    public SQLite(String path) {
        this.path = path;
        if (!isConnected()) {
            try {
                Class.forName("org.sqlite.JDBC");
                con = DriverManager.getConnection("jdbc:sqlite:" + path);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return con;
    }

    public String getPath() {
        return path;
    }

    public boolean isConnected() {
        return (con != null);
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con = null;
        System.gc();
    }
}