package top.fpsmaster.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;

public class ArmorStatus extends Module {

    public BooleanValue damageValue = new BooleanValue("Damage", false);

    public ArmorStatus(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        super.addValues(damageValue);
    }

    @Override
    public void onGui() {
        super.onGui();
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int y1 = 0;
        int wd = 0;
        ItemStack[] armorInventory = mc.thePlayer.inventory.armorInventory;
        if (armorInventory[3] != null) {
            int temp = draw(armorInventory[3], x * sr.getScaledWidth(), y * sr.getScaledHeight());
            if (wd < temp)
                wd = temp;
            y1 += 15;
        }
        if (armorInventory[2] != null) {
            int temp = draw(armorInventory[2], x * sr.getScaledWidth(), y * sr.getScaledHeight() + y1);
            if (wd < temp)
                wd = temp;
            y1 += 15;
        }
        if (armorInventory[1] != null) {
            int temp = draw(armorInventory[1], x * sr.getScaledWidth(), y * sr.getScaledHeight() + y1);
            if (wd < temp)
                wd = temp;
            y1 += 15;
        }
        if (armorInventory[0] != null) {
            int temp = draw(armorInventory[0], x * sr.getScaledWidth(), y * sr.getScaledHeight() + y1);
            if (wd < temp)
                wd = temp;
            y1 += 15;
        }
        if (mc.thePlayer.getHeldItem() != null) {
            int temp = draw(mc.thePlayer.getHeldItem(), x * sr.getScaledWidth(), y * sr.getScaledHeight() + y1);
            if (wd < temp)
                wd = temp;
            y1 += 15;
        }
        width = 15;
        height = y1;
    }

    public int draw(ItemStack item, double x, double y) {

        int temp = 0;
        if (item == null)
            return 0;
        GL11.glPushMatrix();

        RenderItem ir = mc.getRenderItem();

        RenderHelper.enableGUIStandardItemLighting();
        ir.renderItemIntoGUI(item, (int) x, (int) y);
        ir.renderItemOverlays(mc.fontRendererObj, item, (int) x, (int) y);
        RenderHelper.disableStandardItemLighting();
        int damage = item.getMaxDamage() - item.getItemDamage();
        GlStateManager.enableAlpha();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.clear(256);
        if (damageValue.getValue() && damage > 0) {
            FPSMaster.INSTANCE.fontLoader.arial16.drawStringWithShadow(damage + "/" + item.getMaxDamage(), (float) (x + 15 + 2), (int) (y + 6), -1);
            temp = FPSMaster.INSTANCE.fontLoader.arial16.getStringWidth(damage + "/" + item.getMaxDamage());
        }

        GL11.glPopMatrix();
        return temp;
    }
}
