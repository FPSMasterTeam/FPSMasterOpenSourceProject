package top.fpsmaster.gui.keystrokes.keys.impl;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.AbstractKey;

import java.awt.*;

public class MouseButton extends AbstractKey {
    private static final String[] BUTTONS = new String[]{"LMB", "RMB"};
    private final int button;
    private boolean wasPressed = true;
    private long lastPress = 0L;

    public MouseButton(KeyStrokes mod, int button, int xOffset, int yOffset) {
        super(mod, xOffset, yOffset);
        this.button = button;
    }

    int getButton() {
        return this.button;
    }

    public void renderKey(int x, int y) {
        int yOffset = this.yOffset;
        Mouse.poll();
        boolean pressed = Mouse.isButtonDown(this.button) && (mc.currentScreen == null || mc.currentScreen.allowUserInput);
        if (!((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showWASD.getValue()) {
            yOffset -= 48;
        }

        String name = BUTTONS[this.button];
        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            this.lastPress = System.currentTimeMillis();
        }

        int textColor = this.getColor();
        int pressedColor = this.getPressedColor();
        int color;
        double textBrightness;
        if (pressed) {
            color = Math.min(255, (int) (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 5.0D * (double) (System.currentTimeMillis() - this.lastPress)));
            textBrightness = Math.max(0.0D, 1.0D - (double) (System.currentTimeMillis() - this.lastPress) / ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 2.0D)));
        } else {
            color = Math.max(0, 255 - (int) ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 5.0D * (double) (System.currentTimeMillis() - this.lastPress))));
            textBrightness = Math.min(1.0D, (double) (System.currentTimeMillis() - this.lastPress) / ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 2.0D)));
        }

        if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).keyBackground.getValue())) {
            Gui.drawRect(x + this.xOffset, y + yOffset, x + this.xOffset + 34, y + yOffset + 22, pressed ? ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundPressedColor.getColor() : (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundColor.getColor()));
        }

        int red = textColor >> 16 & 255;
        int green = textColor >> 8 & 255;
        int blue = textColor & 255;
        int colorN = (new Color(0, 0, 0)).getRGB() + ((int) ((double) red * textBrightness) << 16) + ((int) ((double) green * textBrightness) << 8) + (int) ((double) blue * textBrightness);
        if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showCPSOnButtons.getValue()) && (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showCPS.getValue())) {
            int round = Math.round((float) y / 0.8F + (float) yOffset / 0.8F + 18.0F);
            CPSKey cpsKey = this.mod.getRenderer().getCPSKeys()[0];
            if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue())) {
                this.drawChromaString(name, x + this.xOffset + 8, y + yOffset + 4, 1.0D);
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.8F, 0.8F, 0.0F);
                this.drawChromaString((name.equals(BUTTONS[0]) ? cpsKey.getLeftCPS() : cpsKey.getRightCPS()) + " CPS", Math.round((float) x / 0.8F + (float) this.xOffset / 0.8F + 10.0F - (mc.fontRendererObjWithoutUnicode.getStringWidth((name.equals(BUTTONS[0]) ? cpsKey.getLeftCPS() : cpsKey.getRightCPS()) + "") / 2.0f)), round, 0.8D);
            } else {
                this.mc.fontRendererObjWithoutUnicode.drawString(name, x + this.xOffset + 8, y + yOffset + 4, pressed ? pressedColor : colorN);
                GlStateManager.pushMatrix();
                GlStateManager.scale(0.8F, 0.8F, 0.0F);
                this.mc.fontRendererObjWithoutUnicode.drawString((name.equals(BUTTONS[0]) ? cpsKey.getLeftCPS() : cpsKey.getRightCPS()) + " CPS", Math.round((float) x / 0.8F + (float) this.xOffset / 0.8F + 10.0F - (mc.fontRendererObjWithoutUnicode.getStringWidth((name.equals(BUTTONS[0]) ? cpsKey.getLeftCPS() : cpsKey.getRightCPS()) + "") / 2.0f)), round, pressed ? pressedColor : colorN);
            }

            GlStateManager.popMatrix();
        } else if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue())) {
            this.drawChromaString(name, x + this.xOffset + 8, y + yOffset + 8, 1.0D);
        } else {
            this.mc.fontRendererObjWithoutUnicode.drawString(name, x + this.xOffset + 8, y + yOffset + 8, pressed ? pressedColor : colorN);
        }

    }
}
