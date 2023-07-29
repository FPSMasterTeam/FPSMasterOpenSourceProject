package top.fpsmaster.modules.render;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;

public class NameTag extends Module {
    public static boolean TOGGLE = false;

    public NameTag(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        TOGGLE = true;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        TOGGLE = false;
    }
}
