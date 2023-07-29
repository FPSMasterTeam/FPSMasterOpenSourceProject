package top.fpsmaster.core.script;

import net.minecraft.client.Minecraft;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.script.api.Values;
import top.fpsmaster.utils.os.FileUtils;
import top.fpsmaster.utils.ui.NotificationType;
import top.fpsmaster.utils.ui.NotificationsUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;

/**
 * @description: 脚本
 * @author: QianXia
 * @create: 2020/11/4 18:08
 **/
public class FpsMasterScript {
    public String name, author, version, category;
    private ScriptModule scriptModule;
    /**
     * 执行脚本中的方法
     */
    public Invocable invoke;

    public FpsMasterScript(File scriptFile) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("JavaScript");
        // 读入脚本内容
        String scriptContent = FileUtils.readFile(scriptFile);
        invoke = (Invocable) scriptEngine;

        // 先跑一遍脚本 肯定报错 为了获取变量
        try {
            scriptEngine.eval(scriptContent);
        } catch (ScriptException ignored) {
        }

        // 获取必要信息
        this.name = (String) scriptEngine.get("name");
        this.author = (String) scriptEngine.get("author");
        this.version = (String) scriptEngine.get("version");
        this.category = (String) scriptEngine.get("category");

        // 从字符串到ModCategory
        ModuleCategory modCategory;
        try {
            modCategory = ModuleCategory.valueOf(this.category);
        } catch (Exception e) {
            e.printStackTrace();
            NotificationsUtils.sendMessage(NotificationType.ERROR, "失败的操作去加载脚本：" + scriptFile.getAbsolutePath());
            NotificationsUtils.sendMessage(NotificationType.ERROR, "功能分类: " + this.category + " 未找到");
            NotificationsUtils.sendMessage(NotificationType.ERROR, "如果Category填写无误请检查语法错误");
            return;
        }

        this.registerModule(name, modCategory, invoke);

        // 传递变量
        manager.put("values", new Values(scriptModule));
        manager.put("out", System.out);
        manager.put("mc", Minecraft.getMinecraft());

        // 再次加载 这次不应该出错 如果出错即为加载失败
        try {
            scriptEngine.eval(scriptContent);
        } catch (ScriptException e) {
            e.printStackTrace();
            NotificationsUtils.sendMessage(NotificationType.ERROR, "Failed to load script" + scriptFile.getAbsolutePath());
        }
    }

    public void registerModule(String moduleName, ModuleCategory category, Invocable invocable) {
        scriptModule = new ScriptModule(moduleName, category, invocable);
        FPSMaster.INSTANCE.moduleManager.scriptModules.put(moduleName, this);
        FPSMaster.INSTANCE.moduleManager.modules.put(moduleName, scriptModule);
    }

    public ScriptModule getScriptModule() {
        return scriptModule;
    }
}
