package live.nerotv.api.cloud.servers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CloudServer {

    private String[] arguments;
    private boolean outputEnabled = true;
    private final String path;
    private final String id;

    public CloudServer(String id, String path, String[] arguments) {
        this.arguments = arguments;
        ArrayList<String> argumentList;
        if(this.arguments==null) {
            argumentList = new ArrayList<>();
            argumentList.add("nogui");
        } else {
            argumentList = new ArrayList<>(Arrays.asList(this.arguments));
            if(!argumentList.contains("nogui")) {
                argumentList.add("nogui");
            }
        }
        this.arguments = argumentList.toArray(new String[0]);
        this.path = path;
        this.id = id;
        if(downloadPaper()) {
            startPaper(1024);
        }
    }

    public boolean isOutputEnabled() {
        return outputEnabled;
    }

    public void setOutputEnabled(boolean outputEnabled) {
        this.outputEnabled = outputEnabled;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String[] getArguments() {
        return arguments;
    }

    private String getLatestPaperDownloadUrl() {
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

    private boolean downloadPaper() {
        if(getLatestPaperDownloadUrl()==null) {
            return false;
        }
        try {
            URL url = new URL(getLatestPaperDownloadUrl());
            InputStream inputStream = url.openConnection().getInputStream();
            FileOutputStream outputStream = new FileOutputStream(path+"paper.jar");
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

    @Deprecated
    public void startPaperInMainThread(long memory) {
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add("-Xmx" + memory + "M");
        command.add(path+"paper.jar");
        if (arguments != null && arguments.length > 0) {
            command.addAll(Arrays.asList(arguments));
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(path));
        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (outputEnabled) {
                    System.out.println(line);
                }
            }
            int exitCode = process.waitFor();
            System.out.println("JAR file closed with exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startPaper(long memory) {
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add("-Xmx" + memory + "M");
        command.add(path+"paper.jar");
        if (arguments != null && arguments.length > 0) {
            command.addAll(Arrays.asList(arguments));
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(new File(path));
        try {
            Process process = processBuilder.start();
            Thread outputThread = new Thread(() -> {
                try {
                    InputStream inputStream = process.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (outputEnabled) {
                            System.out.println(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            outputThread.start();
            Thread processThread = new Thread(() -> {
                try {
                    InputStream errorStream = process.getErrorStream();
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
                    String errorLine;
                    while ((errorLine = errorReader.readLine()) != null) {
                        System.err.println(errorLine);
                    }

                    int exitCode = process.waitFor();
                    System.out.println("JAR-Datei wurde mit Exit-Code beendet: " + exitCode);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
            processThread.start();
            processThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}