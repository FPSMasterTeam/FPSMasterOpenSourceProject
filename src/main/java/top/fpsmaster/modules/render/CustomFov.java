package top.fpsmaster.modules.render;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;

public class CustomFov extends Module {

    public static BooleanValue noSpeedFov = new BooleanValue("NoSpeedFov", true);
    public static BooleanValue noBowFov = new BooleanValue("NoBowFov", true);

    public CustomFov(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        addValues(noBowFov, noSpeedFov);
    }
}
