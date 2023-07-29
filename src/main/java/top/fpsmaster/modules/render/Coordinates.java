package top.fpsmaster.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.ColorValue;

import java.awt.*;

public class Coordinates extends Module {
    public ColorValue color = new ColorValue("Color", new Color(255, 255, 255));

    public Coordinates(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        addValues(color);
    }

    @Override
    public void onGui() {
        super.onGui();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        String c = "X:" + ((int) mc.thePlayer.posX) + " Y:" + ((int) mc.thePlayer.posY) + " Z:" + ((int) mc.thePlayer.posZ);
        mc.fontRendererObj.drawStringWithShadow(c, x * sr.getScaledWidth(), y * sr.getScaledHeight(), color.getColor());
        width = mc.fontRendererObj.getStringWidth(c);
        height = 10;
    }
}
