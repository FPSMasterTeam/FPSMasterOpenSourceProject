package top.fpsmaster.gui.keystrokes.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;

import java.awt.*;

public abstract class AbstractKey extends Gui {
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final KeyStrokes mod;
    protected final int xOffset;
    protected final int yOffset;

    public AbstractKey(KeyStrokes mod, int xOffset, int yOffset) {
        this.mod = mod;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    protected Color getChromaColor(double x, double y, double offsetScale) {
        float v = 2000.0F;
        return new Color(Color.HSBtoRGB((float) (((double) System.currentTimeMillis() - x * 10.0D * offsetScale - y * 10.0D * offsetScale) % (double) v) / v, 0.8F, 0.8F));
    }

    protected void drawChromaString(String text, int x, int y, double offsetScale) {
        mc.fontRendererObjWithoutUnicode.setUnicodeFlag(false);
        char[] var7 = text.toCharArray();
        for (char c : var7) {
            int i = this.getChromaColor(x, y, offsetScale).getRGB();
            String tmp = String.valueOf(c);
            mc.fontRendererObjWithoutUnicode.drawString(tmp, x, y, i);
            x += mc.fontRendererObjWithoutUnicode.getStringWidth(tmp);
        }
    }

    protected abstract void renderKey(int var1, int var2);

    protected final int getColor() {
        return ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue() ? Color.HSBtoRGB((float) ((System.currentTimeMillis() - (long) this.xOffset * 10L - (long) this.yOffset * 10L) % 2000L) / 2000.0F, 0.8F, 0.8F) : ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).color.getColor();
    }

    protected final int getPressedColor() {
        return ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue() ? (new Color(0, 0, 0)).getRGB() : ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).pressedColor.getColor();
    }

    protected final void drawCenteredString(String text, int x, int y, int color) {
        mc.fontRendererObjWithoutUnicode.setUnicodeFlag(false);
        mc.fontRendererObjWithoutUnicode.drawString(text, (float) (x - mc.fontRendererObjWithoutUnicode.getStringWidth(text) / 2), (float) y, color, false);
    }

    protected String getKeyOrMouseName(int keyCode) {
        if (keyCode < 0) {
            String openglName = Mouse.getButtonName(keyCode + 100);
            if (openglName != null) {
                if (openglName.equalsIgnoreCase("button0")) {
                    return "LMB";
                }

                if (openglName.equalsIgnoreCase("button1")) {
                    return "RMB";
                }
            }

            return openglName;
        } else {
            return Keyboard.getKeyName(keyCode);
        }
    }
}
