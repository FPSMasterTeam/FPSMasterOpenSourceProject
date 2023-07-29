package top.fpsmaster.core.I18N;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.data.ConfigManager;
import top.fpsmaster.data.Language;
import top.fpsmaster.utils.os.FileUtils;

import java.io.File;
import java.util.Map;

public class I18NUtils {
    public static void loadLanguage(String lang) {
        File file = new File(ConfigManager.dir + "/languages/" + lang + ".json").getAbsoluteFile();
        Gson gson = new GsonBuilder().create();
        try {
            FPSMaster.INSTANCE.language = gson.fromJson(FileUtils.readFile(file), Language.class);
        } catch (Exception ignored) {
        }
    }

    public static String getString(String name) {
        assert FPSMaster.INSTANCE.language != null;
        String result = FPSMaster.INSTANCE.language.texts.get(name);
        if (result == null) {
            String[] res = name.split("\\.");
            if (res.length > 0) {
                result = res[res.length - 1];
            } else {
                result = name;
            }
        }
        return result;
    }

    public static String getGlobal(String s) {
        for (Map.Entry<String, String> m : FPSMaster.INSTANCE.language.texts.entrySet()) {
            if (s.contains(m.getKey().replace("global.", ""))) {
                return s.replaceAll(m.getKey().replace("global.", ""), m.getValue());
            }
        }
        return s;
    }

}
