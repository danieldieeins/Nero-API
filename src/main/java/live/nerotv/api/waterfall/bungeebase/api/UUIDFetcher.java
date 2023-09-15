package live.nerotv.api.waterfall.bungeebase.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class UUIDFetcher {

    public static String getUUID(String playername) {
        String output = callURL("https://api.mojang.com/users/profiles/minecraft/"+playername);
        if(output.length() < 4) {
            return null;
        } else {
            return output.replace(" ", "").replace("{\"name\":\"" + playername + "\",\"id\":\"", "").replace("\"}", "");
        }
    }

    public static String getName(String uuid) {
        uuid = uuid.replace("-", "");
        String output = UUIDFetcher.callURL("https://sessionserver.mojang.com/session/minecraft/profile/"+uuid);
        String result = output.split("properties")[0];
        result = result.replace("{\n  \"id\" : \"" + uuid + "\",\n  \"name\" : \"", "");
        result = result.replace("\",\n  \"", "");
        if(result.length() < 3||result.length() > 16) {
            return null;
        } else {
            return result;
        }
    }

    public static String callURL(String URL) {
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(URL);
            urlConn = url.openConnection();
            if (urlConn != null) urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;

                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}