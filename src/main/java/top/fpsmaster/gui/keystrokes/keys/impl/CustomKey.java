package top.fpsmaster.gui.keystrokes.keys.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.AbstractKey;
import top.fpsmaster.utils.render.GuiBlock;

import java.awt.*;

public class CustomKey extends AbstractKey {
    private int key;
    private boolean wasPressed = true;
    private long lastPress = 0L;
    private int type;
    private final GuiBlock hitbox = new GuiBlock(0, 0, 0, 0);

    public CustomKey(KeyStrokes mod, int key, int type) {
        super(mod, 0, 0);
        this.key = key;
        this.type = type;
    }

    public GuiBlock getHitbox() {
        return this.hitbox;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    private boolean isButtonDown(int buttonCode) {
        if (buttonCode < 0) {
            return Mouse.isButtonDown(buttonCode + 100);
        } else {
            return buttonCode > 0 && Keyboard.isKeyDown(buttonCode);
        }
    }

    public void renderKey(int x, int y) {
        Keyboard.poll();
        boolean pressed = this.isButtonDown(this.key);
        String name = this.type == 0 ? (!((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue() ? EnumChatFormatting.STRIKETHROUGH + "-----" : "------") : this.getKeyOrMouseName(this.key);
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
            textBrightness = Math.max(0.0D, 1.0D - (double) (System.currentTimeMillis() - this.lastPress) / (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 2.0D));
        } else {
            color = Math.max(0, 255 - (int) (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 5.0D * (double) (System.currentTimeMillis() - this.lastPress)));
            textBrightness = Math.min(1.0D, (double) (System.currentTimeMillis() - this.lastPress) / (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).fadeTime.getValue().doubleValue() * 2.0D));
        }

        int left = x + this.xOffset;
        int top = y + this.yOffset;
        int right;
        int bottom;
        if (this.type != 0 && this.type != 1) {
            right = x + this.xOffset + 22;
            bottom = y + this.yOffset + 22;
        } else {
            right = x + this.xOffset + 70;
            bottom = y + this.yOffset + 16;
        }

        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).keyBackground.getValue()) {
            Gui.drawRect(left, top, right, bottom, ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundColor.getColor());
        }

        this.hitbox.setLeft(left);
        this.hitbox.setTop(top);
        this.hitbox.setRight(right);
        this.hitbox.setBottom(bottom);
        int red = textColor >> 16 & 255;
        int green = textColor >> 8 & 255;
        int blue = textColor & 255;
        int colorN = (new Color(0, 0, 0)).getRGB() + ((int) ((double) red * textBrightness) << 16) + ((int) ((double) green * textBrightness) << 8) + (int) ((double) blue * textBrightness);
        float yPos = (float) (y + this.yOffset + 8);
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
            if (this.type == 0) {
                int xIn = x + (this.xOffset + 76) / 4;
                int y1 = y + this.yOffset + 9;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float) xIn, (float) y1, 0.0F);
                GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                this.drawGradientRect(0, 0, 2, 35, Color.HSBtoRGB((float) ((System.currentTimeMillis() - (long) (xIn * 10L) - (long) (y1 * 10L)) % 2000L) / 2000.0F, 0.8F, 0.8F), Color.HSBtoRGB((float) ((System.currentTimeMillis() - (long) ((xIn + 35) * 10L) - (long) (y1 * 10L)) % 2000L) / 2000.0F, 0.8F, 0.8F));
                GlStateManager.popMatrix();
            } else if (this.type == 1) {
                this.drawChromaString(name, x + (this.xOffset + 70) / 2 - fontRendererObj.getStringWidth(name) / 2, y + this.yOffset + 5, 1.0D);
            } else {
                this.drawChromaString(name, (left + right) / 2 - fontRendererObj.getStringWidth(name) / 2, (int) yPos, 1.0D);
            }
        } else if (this.type != 0 && this.type != 1) {
            mc.fontRendererObjWithoutUnicode.drawString(name, (left + right) / 2 - fontRendererObj.getStringWidth(name) / 2, (int) yPos, pressed ? pressedColor : colorN);
        } else {
            this.drawCenteredString(name, x + (this.xOffset + 70) / 2, y + this.yOffset + 5, pressed ? pressedColor : colorN);
        }

    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
