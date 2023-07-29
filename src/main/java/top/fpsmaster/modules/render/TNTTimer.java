package top.fpsmaster.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.lwjgl.opengl.GL11;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.ColorValue;

import java.awt.*;
import java.text.DecimalFormat;

public class TNTTimer extends Module {
    public static ColorValue color = new ColorValue("Color", new Color(0, 0, 0, 80));
    public static ColorValue bgColor = new ColorValue("BackgroundColor", new Color(0, 0, 0, 80));

    public TNTTimer(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
    }

    public static void doRender(EntityTNTPrimed entity) {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(2929);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        float partialTicks = mc.timer.renderPartialTicks;
        double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks - RenderManager.renderPosX;
        double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks - RenderManager.renderPosY;
        double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks - RenderManager.renderPosZ;
        float scale = 0.065f;
        GlStateManager.translate(((float) x), ((float) y + entity.height + 0.5f - (entity.height / 2.0f)), ((float) z));
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((-mc.getRenderManager().playerViewY), 0.0f, 1.0f, 0.0f);
        GL11.glScalef((-(scale /= 2.0f)), (-scale), (-scale));
        double xLeft = -10.0;
        double xRight = 10.0;
        double yUp = -20.0;
        double yDown = -10.0;
        drawRect((float) xLeft, (float) yUp, (float) xRight, (float) yDown, bgColor.getColor());
        drawTime(entity);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GlStateManager.disableBlend();
        GL11.glDisable(3042);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glNormal3f(1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    private static int getWidth(String text) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
    }

    private static void drawTime(EntityTNTPrimed entity) {
        float width = (float) getWidth("0.00") / 2.0f + 6.0f;
        GlStateManager.disableDepth();
        DecimalFormat df = new DecimalFormat("0.00");
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(df.format((entity.fuse / 20.)), -width + 5.5f, (float) -20.0, color.getColor());
        GlStateManager.enableDepth();
    }

    public static void drawRect(float g, float h, float i, float j, int col1) {
        float f = (float) (col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float) (col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float) (col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float) (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(7);
        GL11.glVertex2d(i, h);
        GL11.glVertex2d(g, h);
        GL11.glVertex2d(g, j);
        GL11.glVertex2d(i, j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
}
