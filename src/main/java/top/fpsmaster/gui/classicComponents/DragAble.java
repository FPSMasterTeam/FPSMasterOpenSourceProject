package top.fpsmaster.gui.classicComponents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.I18N.I18NUtils;
import top.fpsmaster.core.Module;
import top.fpsmaster.gui.guiScreen.GuiEditCustom;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class DragAble {
    public float x, y, x1, y1, dX, dY;
    public boolean drag;
    public Module mod;

    public DragAble(Module module) {
        this.mod = module;
    }

    public void draw(float mouseX, float mouseY) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        x = mod.x * sr.getScaledWidth();
        x *= mod.getScale();
        y = mod.y * sr.getScaledHeight();
        y *= mod.getScale();
        x1 = x + mod.width;
        x1 *= mod.getScale();
        y1 = y + mod.height;
        y1 *= mod.getScale();

        GL11.glScalef(mod.getScale(), mod.getScale(), mod.getScale());

        RenderUtil.drawRect(x, y, x1, y1, new Color(230,230,230,100));
//        RenderUtil.drawRect(x, y - 4, x1, y1, new Color(0, 0, 0, 70).getRGB());
        mod.onGui();

        if(isHovered(x,y, x1, y1, mouseX, mouseY)) {
            assert FPSMaster.INSTANCE.fontLoader != null;
            FPSMaster.INSTANCE.fontLoader.client18.drawStringWithShadow(I18NUtils.getString("mod." + mod.name), x + 4, y - 14, -1);
        }
        GlStateManager.scale(1 / mod.getScale(), 1 / mod.getScale(), 0);
    }

    public void mouse(int mouseX, int mouseY) {
        if (!mod.stage && !(Minecraft.getMinecraft().currentScreen instanceof GuiEditCustom)) {
            return;
        }
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        x = mod.x * sr.getScaledWidth();
        y = mod.y * sr.getScaledHeight();
        x1 = x + mod.width;
        y1 = y + mod.height;


//        if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof GuiEditCustom) {
//            if (isHovered(x, y - 16, x1, y, mouseX / mod.getScale(), mouseY / mod.getScale())) {
//                float wheel = Mouse.getDWheel();
//                if (wheel > 0) {
//                    mod.setScale(0.1f + mod.getScale());
//                } else if (wheel < 0) {
//                    mod.setScale(mod.getScale() - 0.1f);
//                }
//            }
        if (!Mouse.isButtonDown(0)) {
            drag = false;
        }
//        }
        if (drag) {
            float w = x1 - x;
            float h = y1 - y;

            int iX = Math.round((int) (x / 50)) * 50;
            x = mouseX + dX - w;
            if (Math.abs(x - iX) < 5) {
                x = iX;
            } else if (Math.abs(x - (iX + 50)) < 5) {
                x = iX + 50;
            }
            mod.x = x / sr.getScaledWidth();

            int iY = Math.round((int) (y / 50)) * 50;
            y = mouseY + dY - h;
            if (Math.abs(y - iY) < 5) {
                y = iY;
            } else if (Math.abs(y - (iY + 50)) < 5) {
                y = iY + 50;
            }
            mod.y = y / sr.getScaledHeight();
        }
    }

    public void clicked(int mouseX, int mouseY, int button) {
        if (!mod.stage && !(Minecraft.getMinecraft().currentScreen instanceof GuiEditCustom))
            return;
        if (isHovered(x, y, x1, y1, mouseX / mod.getScale(), mouseY / mod.getScale()) && button == 1) {
            mod.onRight();
        }
        if (isHovered(x, y - 16, x1, y1, mouseX / mod.getScale(), mouseY / mod.getScale())) {
            drag = true;
            dX = x1 - mouseX / mod.getScale();
            dY = y1 - mouseY / mod.getScale();
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, float mouseX, float mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    public void draw2() {
        if (!mod.stage || (Minecraft.getMinecraft().currentScreen instanceof GuiEditCustom))
            return;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        x = mod.x * sr.getScaledWidth();
        y = mod.y * sr.getScaledHeight();
        x1 = x + mod.width;
        y1 = y + mod.height;
        GL11.glPushMatrix();
        GL11.glScalef(mod.getScale(), mod.getScale(), mod.getScale());
        mod.onGui();
        GlStateManager.scale(1 / mod.getScale(), 1 / mod.getScale(), 0);
        GL11.glPopMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
    }
}
