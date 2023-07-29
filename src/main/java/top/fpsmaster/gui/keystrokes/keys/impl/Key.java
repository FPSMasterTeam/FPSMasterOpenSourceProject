package top.fpsmaster.gui.keystrokes.keys.impl;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.AbstractKey;

import java.awt.*;

public class Key extends AbstractKey {
    private final KeyBinding key;
    private boolean wasPressed = true;
    private long lastPress = 0L;

    public Key(KeyStrokes mod, KeyBinding key, int xOffset, int yOffset) {
        super(mod, xOffset, yOffset);
        this.key = key;
    }

    private boolean isKeyOrMouseDown(int keyCode) {
        return keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode);
    }

    public void renderKey(int x, int y) {
        Keyboard.poll();
        boolean pressed = this.isKeyOrMouseDown(this.key.getKeyCode());
        String name = this.getKeyOrMouseName(this.key.getKeyCode());
        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            this.lastPress = System.currentTimeMillis();
        }

        int textColor = this.getColor();
        int pressedColor = this.getPressedColor();
        double textBrightness;
        int color;
        if (pressed) {
            color = Math.min(255, (int) (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 5.0D * (double) (System.currentTimeMillis() - this.lastPress)));
            textBrightness = Math.max(0.0D, 1.0D - (double) (System.currentTimeMillis() - this.lastPress) / (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 5.0D));
        } else {
            color = Math.max(0, 255 - (int) (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 5.0D * (double) (System.currentTimeMillis() - this.lastPress)));
            textBrightness = Math.min(1.0D, (double) (System.currentTimeMillis() - this.lastPress) / (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 5.0D));
        }

        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).keyBackground.getValue()) {
            Gui.drawRect(x + this.xOffset, y + this.yOffset, x + this.xOffset + 22, y + this.yOffset + 22, pressed ? ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundPressedColor.getColor() : ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundColor.getColor());
        }

        int keyWidth = 22;
        int red = textColor >> 16 & 255;
        int green = textColor >> 8 & 255;
        int blue = textColor & 255;
        int colorN = (new Color(0, 0, 0)).getRGB() + ((int) ((double) red * textBrightness) << 16) + ((int) ((double) green * textBrightness) << 8) + (int) ((double) blue * textBrightness);
        FontRenderer fontRenderer = this.mc.fontRendererObjWithoutUnicode;
        int stringWidth = fontRenderer.getStringWidth(name);
        float scaleFactor = 1.0F;
        if (stringWidth > keyWidth) {
            scaleFactor = (float) keyWidth / (float) stringWidth;
        }

        GlStateManager.pushMatrix();
        float xPos = (float) (x + this.xOffset + 8);
        float yPos = (float) (y + this.yOffset + 8);
        GlStateManager.scale(scaleFactor, scaleFactor, 1.0F);
        if (scaleFactor != 1.0F) {
            float scaleFactorRec = 1.0F / scaleFactor;
            xPos = (float) (x + this.xOffset) * scaleFactorRec + 1.0F;
            yPos *= scaleFactorRec;
        } else if (name.length() > 1) {
            xPos -= (float) (stringWidth >> 2);
        }

        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).arrowKeys.getValue()) {
            double padding = 5.0D;
            double bottom = (double) (y + this.yOffset + keyWidth) - padding;
            double right = (double) (x + this.xOffset + keyWidth) - padding;
            double left = (double) (x + this.xOffset) + padding;
            double top = (double) (y + this.yOffset) + padding;
            double centerX = (left + right) / 2.0D;
            double centerY = (top + bottom) / 2.0D;
            GlStateManager.translate(centerX, centerY, 0.0D);
            Color baseColor = new Color(pressed ? pressedColor : colorN);
            Color topColor = baseColor;
            Color bottomLeftColor = baseColor;
            Color bottomRightColor = baseColor;
            if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
                topColor = this.getChromaColor(centerX, top, 1.0D);
                bottomLeftColor = this.getChromaColor(left, bottom, 1.0D);
                bottomRightColor = this.getChromaColor(right, bottom, 1.0D);
            }

            int angle = 0;
            if (this.key == this.mc.gameSettings.keyBindLeft) {
                angle = -90;
                if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
                    topColor = this.getChromaColor(centerX, centerY, 1.0D);
                    bottomLeftColor = this.getChromaColor(left, bottom, 1.0D);
                    bottomRightColor = this.getChromaColor(right, top, 1.0D);
                }
            }

            if (this.key == this.mc.gameSettings.keyBindBack) {
                angle = -180;
                if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
                    topColor = this.getChromaColor(centerX, bottom, 1.0D);
                    bottomLeftColor = this.getChromaColor(right, top, 1.0D);
                    bottomRightColor = this.getChromaColor(left, top, 1.0D);
                }
            }

            if (this.key == this.mc.gameSettings.keyBindRight) {
                angle = 90;
                if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
                    topColor = this.getChromaColor(right, centerY, 1.0D);
                    bottomLeftColor = this.getChromaColor(left, top, 1.0D);
                    bottomRightColor = this.getChromaColor(left, bottom, 1.0D);
                }
            }

            GlStateManager.rotate((float) angle, 0.0F, 0.0F, 1.0F);
            left -= centerX;
            right -= centerX;
            centerX = 0.0D;
            top -= centerY;
            bottom -= centerY;
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.shadeModel(7425);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            GlStateManager.color(1.0F, 1.0F, 1.0F);
            worldrenderer.pos(centerX, top, 0.0D).color(topColor.getRed(), topColor.getGreen(), topColor.getBlue(), 255).endVertex();
            worldrenderer.pos(centerX, top, 0.0D).color(topColor.getRed(), topColor.getGreen(), topColor.getBlue(), 255).endVertex();
            worldrenderer.pos(left, bottom, 0.0D).color(bottomLeftColor.getRed(), bottomLeftColor.getGreen(), bottomLeftColor.getBlue(), 255).endVertex();
            worldrenderer.pos(right, bottom, 0.0D).color(bottomRightColor.getRed(), bottomRightColor.getGreen(), bottomLeftColor.getBlue(), 255).endVertex();
            tessellator.draw();
            GlStateManager.shadeModel(7424);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        } else if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
            this.drawChromaString(name, (int) xPos, (int) yPos, 1.0D);
        } else {
            this.mc.fontRendererObjWithoutUnicode.drawString(name, (int) xPos, (int) yPos, pressed ? pressedColor : colorN);
        }

        GlStateManager.popMatrix();
    }
}
