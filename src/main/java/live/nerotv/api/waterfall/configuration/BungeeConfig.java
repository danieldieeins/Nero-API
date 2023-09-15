package live.nerotv.api.waterfall.configuration;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class BungeeConfig {

    private File config;
    private Configuration cfg;

    public BungeeConfig(String path) {
        this.config = new File(path);
        File dir = new File(config.getParent());
        if(!dir.exists()) {
            dir.mkdirs();
        }
        if(!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException ignore) {}
        }
        try {
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config);
        } catch (IOException ignore) {}
        reloadConfig();
    }

    public File getFile() {
        return config;
    }

    public Configuration getCFG() {
        return cfg;
    }

    public void set(String path, Object content) {
        reloadConfig();
        cfg.set(path,content);
        saveConfig();
        reloadConfig();
    }

    public Object get(String path) {
        reloadConfig();
        if(cfg.contains(path)) {
            return cfg.get(path);
        } else {
            return null;
        }
    }

    public void checkEntry(String path, Object content) {
        reloadConfig();
        if(!(config.exists())) {
            cfg.set(path, content);
            saveConfig();
        } else {
            if(!(cfg.contains(path))) {
                cfg.set(path, content);
                saveConfig();
            }
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg,config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        try {
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        cfg = null;
        config.delete();
        config = null;
        System.gc();
    }

    public void unload() {
        saveConfig();
        reloadConfig();
        config = null;
        cfg = null;
        System.gc();
    }
}