package live.nerotv.api.utils;

import java.io.*;
import java.net.URL;

public class VersionChecker {

    public static String version = "unknown";
    public static String newestVersion = "";

    private File downloadFile(String url, String savePath) {
        try {
            URL fileUrl = new URL(url);
            try (BufferedInputStream in = new BufferedInputStream(fileUrl.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(savePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
            return new File(savePath);
        } catch (IOException e) {
            return null;
        }
    }

    private void copyFile(File file, String pathToNewFile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(pathToNewFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    private boolean downloadPlugin() {
        File file = new File("Nero/API/latest.jar");
        if(file.exists()) {
            file.delete();
        }
        File plugin = downloadFile("https://a.nerotv.live/cloud/index.php/s/NeroAPI-R/download","Nero/API/latest.jar");
        return plugin != null;
    }

    private void update() {
        if(downloadPlugin()) {
            File installed = new File("plugins/NeroAPI-R.jar");
            if(installed.exists()) {
                installed.delete();
            }
            try {
                copyFile(new File("Nero/API/latest.jar"),"plugins/"+"NeroAPI-R.jar");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean downloadData() {
        File dataFile = downloadFile("https://a.nerotv.live/cloud/index.php/s/NeroAPI-Data/download", "Nero/API/data.yml");
        if (dataFile != null) {
            org.bukkit.configuration.file.YamlConfiguration data = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(dataFile);
            newestVersion = data.getString("General.Version.Latest");
            data.set("General.Version.Installed", version);
            try {
                data.save(dataFile);
            } catch (IOException ignore) {
            }
            System.out.println(version + " | " + newestVersion);


            if (!version.equals(newestVersion)) {
                update();
                org.bukkit.Bukkit.getServer().shutdown();
            }


            return true;
        } else {
            dataFile = new File("Nero/API/data.yml");
            org.bukkit.configuration.file.YamlConfiguration data = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(dataFile);
            newestVersion = "unknown";
            data.set("General.Version.Installed", version);
            try {
                data.save(dataFile);
            } catch (IOException ignore) {
            }
            System.out.println(version + " | " + newestVersion);
        }
        return false;
    }
}