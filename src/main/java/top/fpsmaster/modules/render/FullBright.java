package top.fpsmaster.modules.render;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;

public class FullBright extends Module {
    private float oldGamma;

    public FullBright(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
    }

    @Override
    public void onEnable() {
        this.oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 15f;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.gammaSetting = this.oldGamma;
        super.onDisable();
    }
}
