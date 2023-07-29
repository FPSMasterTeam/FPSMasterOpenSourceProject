package top.fpsmaster.core.script;

import net.minecraft.client.Minecraft;
import top.fpsmaster.FPSMaster;

import javax.script.ScriptException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 脚本管理器
 * @author: QianXia
 * @create: 2020/11/4 17:25
 **/
public class ScriptManager {
    public List<FpsMasterScript> scripts;
    public File clientDir = new File(Minecraft.getMinecraft().mcDataDir, FPSMaster.CLIENT_NAME);

    public ScriptManager() {
        this.loadScripts();
    }

    public void loadScripts() {
        if (!clientDir.exists()) {
            clientDir.mkdir();
        }
        File scriptDir = new File(clientDir, "javascripts");
        if (!scriptDir.exists()) {
            scriptDir.mkdir();
        }

        // 得到javascripts目录下所有后缀为.js的文件
        File[] scriptsFiles = scriptDir.listFiles((dir, name) -> name.endsWith(".js"));
        if (scriptsFiles == null) {
            return;
        }

        scripts = new ArrayList<>();
        for (File scriptFile : scriptsFiles) {
            FpsMasterScript script = new FpsMasterScript(scriptFile);
            scripts.add(script);
        }
    }


    public void onClientStart(FPSMaster fpsMaster) {
        for (FpsMasterScript script : scripts) {
            try {
                script.invoke.invokeFunction("onClientStart", fpsMaster);
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException ignored) {
            }
        }
    }

    public void onClientStop(FPSMaster fpsMaster) {
        for (FpsMasterScript script : scripts) {
            try {
                script.invoke.invokeFunction("onClientStop", fpsMaster);
            } catch (ScriptException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException ignored) {
            }
        }
    }
}