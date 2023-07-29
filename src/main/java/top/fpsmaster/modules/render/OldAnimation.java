package top.fpsmaster.modules.render;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;

public class OldAnimation extends Module {
    public static BooleanValue oldRod = new BooleanValue("OldRod", false);
    public static BooleanValue oldBlock = new BooleanValue("OldBlock", false);
    public static BooleanValue blockHit = new BooleanValue("BlockHit", false);
    public static BooleanValue oldBow = new BooleanValue("OldBow", false);
    public static BooleanValue oldSwing = new BooleanValue("OldSwing", false);

    public OldAnimation(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        this.addValues(oldBlock, oldRod, blockHit, oldBow, oldSwing);
        this.canBeEnabled = false;
    }
}
