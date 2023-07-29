package top.fpsmaster.gui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import top.fpsmaster.gui.font.ttfr.StringCache;

import java.awt.*;

public class UFontRenderer extends FontRenderer {
    public int FONT_HEIGHT = 8;
    private StringCache stringCache;
    public int[] colorCode = new int[32];

    public UFontRenderer(Font font, int size, boolean antiAlias) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().renderEngine, false);
        ResourceLocation res = new ResourceLocation("textures/font/ascii.png");
        for (int i = 0; i < 32; ++i) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;

            if (i == 6) {
                k += 85;
            }

            if (Minecraft.getMinecraft().gameSettings.anaglyph) {
                int j1 = (k * 30 + l * 59 + i1 * 11) / 100;
                int k1 = (k * 30 + l * 70) / 100;
                int l1 = (k * 30 + i1 * 70) / 100;
                k = j1;
                l = k1;
                i1 = l1;
            }

            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            this.colorCode[i] = (k & 255) << 16 | (l & 255) << 8 | i1 & 255;
        }


        if (res.getResourcePath().equalsIgnoreCase("textures/font/ascii.png") && this.getStringCache() == null) {
            this.setStringCache(new StringCache(colorCode));
            this.getStringCache().setDefaultFont(font, size, antiAlias);
        }
    }


    public StringCache getStringCache() {
        return stringCache;
    }

    public void setStringCache(StringCache value) {
        stringCache = value;
    }


    /**
     * Draws the specified string with a shadow.
     */
    public int drawStringWithShadow(String text, float x, float y, int color) {
        return this.drawString(text, x, y, color, true);
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String text, float x, int y, int color) {
        return this.drawString(text, x, (float) y, color, false);
    }

    public int drawString(String text, float x, float y, int color) {
        this.drawString(text, x, y, color, false);
        return getStringWidth(text);
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        int i;
        GlStateManager.enableBlend();
        if (dropShadow) {
            getStringCache().renderString(text, x + 1.0F, y + 1.0F, new Color(50, 50, 50, 200).getRGB(), true);
            i = Math.max(0, getStringCache().renderString(text, x, y, color, false));
        } else {
            i = getStringCache().renderString(text, x, y, color, false);
        }
        GlStateManager.disableBlend();


        return i;
    }


    public int getStringWidth(String text) {
        return getStringCache().getStringWidth(text);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        getStringCache().renderString(text, x - getStringCache().getStringWidth(text) / 2f, y, color, false);
    }

    public float getHeight() {
        return 8;
    }

    public String trimStringToWidth(String text, int width) {
        return getStringCache().trimStringToWidth(text, width, false);
    }

    public String trimStringToWidth(String text, int width, boolean reverse) {
        return getStringCache().trimStringToWidth(text, width, reverse);
    }
}
