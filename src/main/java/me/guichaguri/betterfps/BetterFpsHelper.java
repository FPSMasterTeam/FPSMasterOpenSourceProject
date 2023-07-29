package me.guichaguri.betterfps;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

/**
 * @author Guilherme Chaguri
 */
public class BetterFpsHelper {
    public static final String VERSION = "1.2.1";

    // Config Name, Class Name
    public static final LinkedHashMap<String, String> helpers = new LinkedHashMap<>();

    // Config Name, Display Name
    public static final LinkedHashMap<String, String> displayHelpers = new LinkedHashMap<>();

    static {
        helpers.put("vanilla", "VanillaMath");
        helpers.put("rivens", "RivensMath");
        helpers.put("libgdx", "LibGDXMath");
        helpers.put("rivens-full", "RivensFullMath");
        helpers.put("rivens-half", "RivensHalfMath");
        helpers.put("java", "JavaMath");

        displayHelpers.put("vanilla", "Vanilla Algorithm");
        displayHelpers.put("rivens", "Riven's Algorithm");
        displayHelpers.put("libgdx", "LibGDX's Algorithm");
        displayHelpers.put("rivens-full", "Riven's \"Full\" Algorithm");
        displayHelpers.put("rivens-half", "Riven's \"Half\" Algorithm");
        displayHelpers.put("java", "Java Math");
    }

    public static File LOC;
    public static File MCDIR = null;
    private static File CONFIG_FILE = null;

    public static void init() {

    }

    public static void loadConfig() {

        if(MCDIR == null) {
            CONFIG_FILE = new File("FPSMaster/configs/betterfps.json").getAbsoluteFile();
        } else {
            CONFIG_FILE = new File(MCDIR, "FPSMaster/configs/betterfps.json").getAbsoluteFile();
        }

        try {
            if(CONFIG_FILE.exists()) {
                Gson gson = new Gson();
                BetterFpsConfig.instance = gson.fromJson(new FileReader(CONFIG_FILE), BetterFpsConfig.class);
            } else {
                BetterFpsConfig.instance = new BetterFpsConfig();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        // Temporary code - Import config from the old format to the new one
        try {
            Properties prop = new Properties();
            File oldConfigFile;
            if(MCDIR == null) {
                oldConfigFile = new File("betterfps.txt");
            } else {
                oldConfigFile = new File(MCDIR, "betterfps.txt");
            }
            if((oldConfigFile.exists()) && (!CONFIG_FILE.exists())) {
                prop.load(Files.newInputStream(oldConfigFile.toPath()));
                BetterFpsConfig.instance.algorithm = prop.getProperty("algorithm", "rivens-half");
            }
        } catch(Exception ex) {
            System.err.println("Could not import the old config format");
        }
        // ---

        saveConfig();

    }

    public static void saveConfig() {
        try {
            if (!CONFIG_FILE.exists()) {
                if (!CONFIG_FILE.createNewFile()) {
                    return;
                }
            }
            Gson gson = new Gson();
            FileUtils.writeStringToFile(CONFIG_FILE, gson.toJson(BetterFpsConfig.instance));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
