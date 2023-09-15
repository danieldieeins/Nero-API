package live.nerotv.api.cloud.servers;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ServerManager {

    public static final HashMap<String, CloudServer> servers = new HashMap<>();

    public static CloudServer createServer(ServerType type, String name) {
        File jarFile = new File(ServerManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String path = URLDecoder.decode(jarFile.getParent().replace(File.separator+"plugins",File.separator+"Nero"+File.separator+type.toString().toLowerCase()+"Servers"+File.separator+name+File.separator), StandardCharsets.UTF_8);
        System.out.println(path);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if(!servers.containsKey(name)) {
            servers.put(name,new CloudServer(name,path,null));
        }
        return getServer(name);
    }

    public static CloudServer getServer(String serverID) {
        if(servers.containsKey(serverID)) {
            return servers.get(serverID);
        }
        return null;
    }
}