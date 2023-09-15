package live.nerotv.api.waterfall.bungeebase.api;

import live.nerotv.api.waterfall.bungeebase.BungeeBase;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigAPI {

    public static File Config = new File("plugins/BungeeBase/config.yml");
    public static File FailedConfig = new File("plugins/BungeeBase/config.yml");
    public static Configuration CFG; {
        try {
            CFG = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration FCFG; {
        try {
            FCFG = ConfigurationProvider.getProvider(YamlConfiguration.class).load(FailedConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration loadConfiguration(File file) {
        Configuration cfg = FCFG;
        try {
            File dir = new File("plugins/BungeeBase");
            if(!dir.exists()) {
                dir.mkdir();
            }
            File dir2 = new File("plugins/BungeeBase/Players");
            if(!dir2.exists()) {
                dir2.mkdir();
            }
            if(!file.exists()) {
                file.createNewFile();
            }
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            cfg.set("API.Plugin.Version", BungeeBase.getVersion());
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg,file);
        } catch (IOException e) {

        }
        return cfg;
    }

    public static void loadConfiguration() {
        try {
            File dir = new File("plugins/BungeeBase");
            if(!dir.exists()) {
                dir.mkdir();
            }
            File dir2 = new File("plugins/BungeeBase/Players");
            if(!dir2.exists()) {
                dir2.mkdir();
            }
            File file = new File(dir.getPath(),"config.yml");
            if(!file.exists()) {
                file.createNewFile();
            }
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            config.set("API.Plugin.Version", BungeeBase.getVersion());
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration getPlayerConfiguration(ProxiedPlayer player) {
        UUID UID = player.getUniqueId();
        String SID = UID.toString();
        SID = SID.replace("-","");
        File PlayerFile = new File("plugins/BungeeBase/Players/"+SID+".yml");
        return loadConfiguration(PlayerFile);
    }

    public static Configuration getPlayerConfiguration(String SID) {
        SID = SID.replace("-","");
        File PlayerFile = new File("plugins/BungeeBase/Players/"+SID+".yml");
        return loadConfiguration(PlayerFile);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void checkEntry(String path,Object content, File file, Configuration cfg) {
        reloadConfig(file,cfg);
        if(!(file.exists())) {
            cfg.set(path, content);
            saveConfig(file,cfg);
        } else {
            if(!(cfg.contains(path))) {
                cfg.set(path, content);
                saveConfig(file,cfg);
            }
        }
    }

    public static void saveConfig(File file,Configuration config) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(CFG,new File("plugins/BungeeBase/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig(File file,Configuration cfg) {
        try {
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig() {
        loadConfiguration();
        try {
            CFG = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("plugins/BungeeBase/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}