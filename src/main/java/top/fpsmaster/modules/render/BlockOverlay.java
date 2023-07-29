package top.fpsmaster.modules.render;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.ColorValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.player.EventType;
import top.fpsmaster.event.events.impl.render.EventRender3D;
import top.fpsmaster.utils.math.ColorUtils;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class BlockOverlay extends Module {
    private final ColorValue color1 = new ColorValue("OutlineColor", new Color(255, 255, 255));
    private final ColorValue color = new ColorValue("FillColor", new Color(255, 255, 255, 50));
    private final BooleanValue outlined = new BooleanValue("Outlined", true);
    private final BooleanValue fill = new BooleanValue("Fill", false);
    private final BooleanValue chroma = new BooleanValue("Chroma", false);
    private final BooleanValue throughBlock = new BooleanValue("ThroughBlock", true);

    public BlockOverlay(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        this.addValues(color, color1, outlined, fill, chroma, throughBlock);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onRender3D(final EventRender3D event) {
        if (event.type == EventType.PRE) {
            return;
        }
        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos pos = mc.objectMouseOver.getBlockPos();
            final Block block = mc.theWorld.getBlockState(pos).getBlock();
            final double n = pos.getX();
            final double x = n - RenderManager.renderPosX;
            final double n2 = pos.getY();
            final double y = n2 - RenderManager.renderPosY;
            final double n3 = pos.getZ();
            final double z = n3 - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(2848);
            if (throughBlock.getValue()) {
                GL11.glDisable(2929);
            }
            GL11.glDepthMask(false);
            int chromaColor = ColorUtils.reAlpha(Color.getHSBColor((System.currentTimeMillis() % 3000) / 3000F, 0.8F, 1F).getRGB(), color.getAlpha() / 255.0f);
            Color c = chroma.getValue() ? ColorUtils.intToColor(chromaColor) : ColorUtils.intToColor(color1.getColor());
            GlStateManager.color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
            final double minX = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0 : block.getBlockBoundsMinX();
            final double minY = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0 : block.getBlockBoundsMinY();
            final double minZ = (block instanceof BlockStairs || Block.getIdFromBlock(block) == 134) ? 0 : block.getBlockBoundsMinZ();
            if (fill.getValue()) {
                RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX - 0.005, y + minY - 0.005, z + minZ - 0.005, x + block.getBlockBoundsMaxX() + 0.005, y + block.getBlockBoundsMaxY() + 0.005, z + block.getBlockBoundsMaxZ() + 0.005));
            }
            GlStateManager.color(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, color1.getAlpha());
            GL11.glLineWidth(1f);
            if (outlined.getValue()) {
                RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + minX - 0.005, y + minY - 0.005, z + minZ - 0.005, x + block.getBlockBoundsMaxX() + 0.005, y + block.getBlockBoundsMaxY() + 0.005, z + block.getBlockBoundsMaxZ() + 0.005));
            }
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            if (throughBlock.getValue()) {
                GL11.glEnable(2929);
            }
            GL11.glDepthMask(true);
            GL11.glPopMatrix();
        }
    }
}
