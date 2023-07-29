package top.fpsmaster.data;

import com.google.gson.*;
import net.minecraft.client.Minecraft;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.utils.os.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ConfigManager {
    public static File dirFile = new File(Minecraft.getMinecraft().mcDataDir, FPSMaster.CLIENT_NAME);
    public static String language = "English";
    public List<String> supported = new ArrayList<>();
    public static String dir = dirFile.getAbsolutePath();
    public Map<String, Boolean> settings = new HashMap<>();
    private final Configure configure = new Configure();

    public ConfigManager() {
        supported.add("English");
        supported.add("Chinese-Simplify");
        supported.add("Japanese");

        File f;
        if (!(f = new File(dir + "/languages").getAbsoluteFile()).exists()) {
            f.mkdirs();
        }
        if (!(f = new File(dir + "/configs").getAbsoluteFile()).exists()) {
            f.mkdirs();
        }
        if (!(f = new File(dir + "/themes").getAbsoluteFile()).exists()) {
            f.mkdirs();
        }
        if (!new File(dir + "/themes/default.json").getAbsoluteFile().exists()) {
            FileUtils.saveFile(dir + "/themes/default.json", new GsonBuilder().create().toJson(FPSMaster.INSTANCE.theme));
        } else {
            FPSMaster.INSTANCE.theme = new GsonBuilder().create().fromJson(FileUtils.readFile(dir + "/themes/default.json"), Theme.class);
        }
        if (!(f = new File(dir + "/javascripts").getAbsoluteFile()).exists()) {
            f.mkdirs();
        }

        for (String s : supported) {
            f = new File(dir + "/languages/" + s + ".json").getAbsoluteFile();
            try {
                f.createNewFile();
                InputStream resourceAsStream = this.getClass().getResourceAsStream("/assets/minecraft/client/language/" + s + ".json");
                if (resourceAsStream == null) {
                    FPSMaster.INSTANCE.logger.info("An error occurred while loading language file: " + s + ".json");
                    continue;
                }
                InputStreamReader in = new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(in);
                StringBuilder sb = new StringBuilder();
                String s1;
                while ((s1 = reader.readLine()) != null) {
                    sb.append(s1);
                }
                FileUtils.saveFile(f.getAbsolutePath(), sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!new File(dir + "/configs/current.json").getAbsoluteFile().exists()) {
            saveConfig("current");
        } else {
            loadConfig("current");
        }
    }

    public void loadConfig(String name) {
        FPSMaster.INSTANCE.logger.info("Loading Config: " + name);
        String readFile = FileUtils.readFile(new File(dir + "/configs/current.json").getAbsoluteFile());
        configure.setConfig(readFile);
        FPSMaster.INSTANCE.logger.info("Loaded Config: " + name);
    }

    public void toggle(String name) {
        this.settings.putIfAbsent(name, false);
        settings.replace(name, !settings.get(name));
    }

    public void saveConfig(String name) {
        FPSMaster.INSTANCE.logger.info("Saved Config : " + name);
        File file = new File(dir + "/configs/" + name + ".json").getAbsoluteFile();
        FileUtils.saveFile(file.getAbsolutePath(), configure.getConfig());
    }

    public void reloadLanguages() {
        supported.clear();
        for (File f : Objects.requireNonNull(new File(dir + "/languages/").getAbsoluteFile().listFiles())) {
            if (f.getAbsolutePath().endsWith(".json")) {
                supported.add(f.getName().replace(".json", ""));
            }
        }
    }
}
