package top.fpsmaster.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.ColorValue;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class Crosshair extends Module {
    private static final BooleanValue dynamic = new BooleanValue("dynamic", true);
    private static final NumberValue<Number> gap = new NumberValue<>("gap", 5.0, 0.25, 15.0, 0.25);
    private static final NumberValue<Number> width = new NumberValue<>("width", 1.0, 0.25, 10.0, 0.25);
    private static final NumberValue<Number> size = new NumberValue<>("size", 7.0, 0.25, 15.0, 0.25);
    public static ColorValue color = new ColorValue("Color", new Color(255, 255, 255));
    public static ColorValue boardColor = new ColorValue("BoardColor", new Color(0, 0, 0, 50));

    public Crosshair(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        this.addValues(dynamic, gap, width, size, color);
    }

    public static void render() {
        double gap = Crosshair.gap.getValue().doubleValue();
        double width = Crosshair.width.getValue().doubleValue();
        double size = Crosshair.size.getValue().doubleValue();
        ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
        RenderUtil.rectangleBordered(
                (double) scaledRes.getScaledWidth() / 2 - width,
                (double) scaledRes.getScaledHeight() / 2 - gap - size - (isMoving() ? 2 : 0),
                (double) scaledRes.getScaledWidth() / 2 + 1.0f + width,
                (double) scaledRes.getScaledHeight() / 2 - gap - (isMoving() ? 2 : 0), 0.5f, color.getColor(),
                boardColor.getColor());
        RenderUtil.rectangleBordered(
                (double) scaledRes.getScaledWidth() / 2 - width,
                (double) scaledRes.getScaledHeight() / 2 + gap + 1 + (isMoving() ? 2 : 0) - 0.15,
                (double) scaledRes.getScaledWidth() / 2 + 1.0f + width,
                (double) scaledRes.getScaledHeight() / 2 + 1 + gap + size + (isMoving() ? 2 : 0) - 0.15, 0.5f, color.getColor(),
                boardColor.getColor());
        RenderUtil.rectangleBordered(
                (double) scaledRes.getScaledWidth() / 2 - gap - size - (isMoving() ? 2 : 0) + 0.15,
                (double) scaledRes.getScaledHeight() / 2 - width,
                (double) scaledRes.getScaledWidth() / 2 - gap - (isMoving() ? 2 : 0) + 0.15,
                (double) scaledRes.getScaledHeight() / 2 + 1.0f + width, 0.5f, color.getColor(),
                boardColor.getColor());
        RenderUtil.rectangleBordered(
                (double) scaledRes.getScaledWidth() / 2 + 1 + gap + (isMoving() ? 2 : 0),
                (double) scaledRes.getScaledHeight() / 2 - width,
                (double) scaledRes.getScaledWidth() / 2 + size + gap + 1 + (isMoving() ? 2 : 0),
                (double) scaledRes.getScaledHeight() / 2 + 1.0f + width, 0.5f, color.getColor(),
                boardColor.getColor());
    }

    private static boolean isMoving() {
        Minecraft mc = Minecraft.getMinecraft();
        return dynamic.getValue() && (!mc.thePlayer.isCollidedHorizontally) && (!mc.thePlayer.isSneaking()) && ((mc.thePlayer.movementInput.moveForward != 0.0F) || (mc.thePlayer.movementInput.moveStrafe != 0.0F));
    }
}
