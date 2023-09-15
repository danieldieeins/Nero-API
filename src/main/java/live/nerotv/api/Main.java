package live.nerotv.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static String[] arguments;
    public static String path = URLDecoder.decode(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent()+File.separator, StandardCharsets.UTF_8);


    public static void main(String[] args) {
        if(downloadPaper()) {
            try {
                installPlugin();
            } catch (IOException e) {
                e.printStackTrace();
            }
            arguments = args;
            startPaper();
        } else {
            if(new File(path+"server.jar").exists()) {
                startPaper();
            } else {
                System.out.println("Download failed. Couldn't start Paper.");
            }
        }
    }

    private static void installPlugin() throws IOException {
        File pluginsFolder = new File(path+"plugins/");
        if(!pluginsFolder.exists()) {
            pluginsFolder.mkdirs();
        }
        try {
            Files.copy(Path.of(path+getJarFileName()), Path.of(path+"plugins/NeroAPI-R.jar"));
        } catch (FileAlreadyExistsException ignore) {}
    }

    private static String getJarFileName() {
        String folder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getAbsolutePath();
        String jarFilePath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsolutePath();
        return jarFilePath.replace(folder,"").replace(File.separator,"");
    }

    private static String getLatestPaperDownloadUrl() {
        try {
            URL projectsUrl = new URL("https://papermc.io/api/v2/projects/paper");
            InputStream projectsInputStream = projectsUrl.openStream();
            JsonElement projectsJsonElement = JsonParser.parseReader(new InputStreamReader(projectsInputStream));
            JsonObject projectsJsonObject = projectsJsonElement.getAsJsonObject();
            JsonArray versions = projectsJsonObject.get("versions").getAsJsonArray();
            String latestVersion = versions.get(versions.size()-1).getAsString();
            URL versionUrl = new URL("https://papermc.io/api/v2/projects/paper/versions/" + latestVersion);
            InputStream versionInputStream = versionUrl.openStream();
            JsonElement versionJsonElement = JsonParser.parseReader(new InputStreamReader(versionInputStream));
            JsonObject versionJsonObject = versionJsonElement.getAsJsonObject();
            JsonArray builds = versionJsonObject.get("builds").getAsJsonArray();
            if(builds.size()>0) {
                String latestBuild = builds.get(builds.size()-1).getAsString();
                return "https://api.papermc.io/v2/projects/paper/versions/%%version%%/builds/%%build%%/downloads/paper-%%version%%-%%build%%.jar".replace("%%version%%",latestVersion).replace("%%build%%",latestBuild);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean downloadPaper() {
        if(getLatestPaperDownloadUrl()==null) {
            return false;
        }
        try {
            URL url = new URL(getLatestPaperDownloadUrl());
            InputStream inputStream = url.openConnection().getInputStream();
            FileOutputStream outputStream = new FileOutputStream(path+"server.jar");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void startPaper() {
        String jarFilePath = path+"server.jar";
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add("-Xmx"+Runtime.getRuntime().maxMemory());
        command.add(jarFilePath);
        if (arguments != null && arguments.length>0) {
            command.addAll(Arrays.stream(arguments).toList());
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("JAR file closed with exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}