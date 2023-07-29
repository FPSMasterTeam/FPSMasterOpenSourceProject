package top.fpsmaster.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.ColorValue;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;
import java.util.Collection;

public class PotionDisplay extends Module {
    public BooleanValue background = new BooleanValue("Background", false);
    public BooleanValue blur = new BooleanValue("Blur", false);
    public ColorValue color = new ColorValue("BackgroundColor", new Color(0, 0, 0, 80));

    public PotionDisplay(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        this.addValues(background, blur, color);
    }

    @Override
    public void onGui() {
        super.onGui();
        ScaledResolution sr = new ScaledResolution(mc);
        float i = x * sr.getScaledWidth();
        float j = y * sr.getScaledHeight();
        width = 166;
        Collection<PotionEffect> collection = this.mc.thePlayer.getActivePotionEffects();

        if (!collection.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int l = 33;

            if (collection.size() > 5) {
                l = 132 / (collection.size() - 1);
            }

            for (PotionEffect potioneffect : this.mc.thePlayer.getActivePotionEffects()) {
                if (blur.getValue()) {
//                    BlurBuffer.blurArea(i + 1, j + 1, i + 164, j + l - 1, true);
                }
                if (background.getValue()) {
                    RenderUtil.drawRect(i + 1, j + 1, i + 164, j + l - 1, color.getColor());
                }
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.enableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));

                if (potion.hasStatusIcon()) {
                    int i1 = potion.getStatusIconIndex();
                    Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(i + 6, j + 7, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
                }

                String s1 = I18n.format(potion.getName());

                if (potioneffect.getAmplifier() == 1) {
                    s1 = s1 + " " + I18n.format("enchantment.level.2");
                } else if (potioneffect.getAmplifier() == 2) {
                    s1 = s1 + " " + I18n.format("enchantment.level.3");
                } else if (potioneffect.getAmplifier() == 3) {
                    s1 = s1 + " " + I18n.format("enchantment.level.4");
                }

                mc.fontRendererObj.drawStringWithShadow(s1, i + 10 + 18, j + 6, 16777215);
                String s = Potion.getDurationString(potioneffect);
                mc.fontRendererObj.drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, 8355711);
                j += l;
            }
            height = j - y * sr.getScaledHeight();
        }
    }
}
