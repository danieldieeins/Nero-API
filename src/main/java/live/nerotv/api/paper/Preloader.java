package live.nerotv.api.paper;

import live.nerotv.api.utils.VersionChecker;
import live.nerotv.api.utils.storage.types.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Preloader extends JavaPlugin {

    private Nero api;
    public VersionChecker updater = new VersionChecker();

    @Override
    public void onLoad() {
        new File("Nero/SQL/").mkdirs();
        Config cfg = new Config("Nero/API/config.yml");
        cfg.checkEntry("Settings.autoUpdate",true);
        if(cfg.getCFG().getBoolean("Settings.autoUpdate")) {
            VersionChecker.version = getDescription().getVersion();
            updater.downloadData();
            if(VersionChecker.version.equals(VersionChecker.newestVersion)||VersionChecker.newestVersion.equalsIgnoreCase("unknown")) {
                api = new Nero();
                api.onLoad(this);
            } else {
                Bukkit.shutdown();
            }
        } else {
            api = new Nero();
            api.onLoad(this);
        }
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