package top.fpsmaster.gui.keystrokes.keys.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.AbstractKey;

import java.awt.*;

public class SpaceKey extends AbstractKey {
    private final KeyBinding key;
    private boolean wasPressed = true;
    private long lastPress = 0L;
    private final String name;

    public SpaceKey(KeyStrokes mod, KeyBinding key, int xOffset, int yOffset, String name) {
        super(mod, xOffset, yOffset);
        this.key = key;
        this.name = name;
    }

    private boolean isButtonDown(int buttonCode) {
        if (buttonCode < 0) {
            return Mouse.isButtonDown(buttonCode + 100);
        } else {
            return buttonCode > 0 && Keyboard.isKeyDown(buttonCode);
        }
    }

    public void renderKey(int x, int y) {
        int yOffset = this.yOffset;
        if (!((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showMouseButtons.getValue()) {
            yOffset -= 24;
        }

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showSpacebar.getValue())) {
            yOffset -= 18;
        }

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showWASD.getValue())) {
            yOffset -= 48;
        }

        Keyboard.poll();
        boolean pressed = this.isButtonDown(this.key.getKeyCode());
        String name = this.name.equalsIgnoreCase("space") ? EnumChatFormatting.STRIKETHROUGH + "------" : "Sneak";
        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            this.lastPress = System.currentTimeMillis();
        }

        int textColor = this.getColor();
        int pressedColor = this.getPressedColor();
        double fadeTime = (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue());
        double textBrightness;
        if (pressed) {
            textBrightness = Math.max(0.0D, 1.0D - (double) (System.currentTimeMillis() - this.lastPress) / (fadeTime * 2.0D));
        } else {
            textBrightness = Math.min(1.0D, (double) (System.currentTimeMillis() - this.lastPress) / (fadeTime * 2.0D));
        }

        int red;
        int green;
        int blue;
        int colorN;
        int xIn;
        int y1;
        if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).keyBackground.getValue())) {
            colorN = x + this.xOffset;
            xIn = y + yOffset;

            Gui.drawRect(colorN, xIn, colorN + 70, xIn + 16, pressed ? ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundPressedColor.getColor() : ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundColor.getColor());
        }

        red = textColor >> 16 & 255;
        green = textColor >> 8 & 255;
        blue = textColor & 255;
        colorN = (new Color(0, 0, 0)).getRGB() + ((int) ((double) red * textBrightness) << 16) + ((int) ((double) green * textBrightness) << 8) + (int) ((double) blue * textBrightness);
        if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue())) {
            if (this.name.equalsIgnoreCase("space")) {
                xIn = x + (this.xOffset + 76) / 4;
                y1 = y + yOffset + 9;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) xIn, (float) y1, 0.0F);
                GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                this.drawGradientRect(0, 0, 2, 35, Color.HSBtoRGB((float) ((System.currentTimeMillis() - (long) xIn * 10L - (long) y1 * 10L) % 2000L) / 2000.0F, 0.8F, 0.8F), Color.HSBtoRGB((float) ((System.currentTimeMillis() - (long) (xIn + 35) * 10L - (long) y1 * 10L) % 2000L) / 2000.0F, 0.8F, 0.8F));
                GlStateManager.popMatrix();
            } else {
                this.drawChromaString(name, x + (this.xOffset + 70) / 2 - Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) / 2, y + yOffset + 5, 1.0D);
            }
        } else {
            this.drawCenteredString(name, x + (this.xOffset + 70) / 2, y + yOffset + 5, pressed ? pressedColor : colorN);
        }

    }
}
