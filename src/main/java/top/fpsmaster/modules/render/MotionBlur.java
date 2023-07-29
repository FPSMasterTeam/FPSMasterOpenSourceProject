package top.fpsmaster.modules.render;

import org.lwjgl.opengl.GL11;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.ModeValue;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.render.DisplayFrameEvent;

public class MotionBlur extends Module {
    NumberValue<Number> multiplier = new NumberValue<>("FrameMultiplier", 0.5, 0.05, 0.99, 0.05);
    ModeValue modeValue = new ModeValue("Mode", "Classic", "New", "Classic");

    public MotionBlur(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        addValues(multiplier, modeValue);
    }


    @EventTarget
    public void onClientTick(DisplayFrameEvent event) {
        final float n = multiplier.getValue().floatValue();
        GL11.glAccum(259, n);
        GL11.glAccum(256, 1.0f - n);
        GL11.glAccum(258, 1.0f);
    }
}
