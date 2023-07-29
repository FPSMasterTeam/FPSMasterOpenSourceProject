package net.minecraft.client.renderer.entity;

import java.util.Random;

import top.fpsmaster.FPSMaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEntityItem extends Render<EntityItem>
{
    private final RenderItem itemRenderer;
    private final Random field_177079_e = new Random();
    private final Random random = new Random();

    public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_)
    {
        super(renderManagerIn);
        this.itemRenderer = p_i46167_2_;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_)
    {
        ItemStack itemstack = itemIn.getEntityItem();
        Item item = itemstack.getItem();

        if (item == null)
        {
            return 0;
        }
        else
        {
            boolean flag = p_177077_9_.isGui3d();
            int i = this.func_177078_a(itemstack);
            float f = 0.25F;
            float f1 = MathHelper.sin(((float)itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
            float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
            GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);

            if (flag || this.renderManager.options != null)
            {
                float f3 = (((float)itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float)Math.PI);
                GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            }

            if (!flag)
            {
                float f6 = -0.0F * (float)(i - 1) * 0.5F;
                float f4 = -0.0F * (float)(i - 1) * 0.5F;
                float f5 = -0.046875F * (float)(i - 1) * 0.5F;
                GlStateManager.translate(f6, f4, f5);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }
    }

    private int func_177078_a(ItemStack stack)
    {
        int i = 1;

        if (stack.stackSize > 48)
        {
            i = 5;
        }
        else if (stack.stackSize > 32)
        {
            i = 4;
        }
        else if (stack.stackSize > 16)
        {
            i = 3;
        }
        else if (stack.stackSize > 1)
        {
            i = 2;
        }

        return i;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        ItemStack itemstack = entity.getEntityItem();
        if (FPSMaster.INSTANCE.moduleManager.modules.get("ItemPhysics").stage) {
            double rotation1 = 5.5D;
            int i;
            if (itemstack != null && itemstack.getItem() != null) {
                i = Item.getIdFromItem(itemstack.getItem()) + itemstack.getMetadata();
            } else {
                i = 187;
            }

            random.setSeed(i);
            this.bindTexture(TextureMap.locationBlocksTexture);
            this.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            IBakedModel ibakedmodel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemstack);
            boolean flag1 = ibakedmodel.isGui3d();
            boolean is3D = ibakedmodel.isGui3d();
            int j = this.getModelCount(itemstack);
            GlStateManager.translate((float) x, (float) y, (float) z);
            if (ibakedmodel.isGui3d()) {
                GlStateManager.scale(0.5F, 0.5F, 0.5F);
            }

            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(entity.rotationYaw, 0.0F, 0.0F, 1.0F);
            if (is3D) {
                GlStateManager.translate(0.0D, 0.0D, -0.08D);
            } else {
                GlStateManager.translate(0.0D, 0.0D, -0.04D);
            }

            if (is3D || Minecraft.getMinecraft().getRenderManager().options != null) {
                double rotation;
                if (is3D) {
                    if (!entity.onGround) {
                        rotation = rotation1 * 2.0D;
                        rotation /= 10;
                        entity.rotationPitch = (float) ((double) entity.rotationPitch + rotation);
                    }
                } else if (!Double.isNaN(entity.posX) && !Double.isNaN(entity.posY) && !Double.isNaN(entity.posZ) && entity.worldObj != null) {
                    if (entity.onGround) {
                        entity.rotationPitch = 0.0F;
                    } else {
                        rotation = rotation1 * 2.0D;
                        rotation /= 10;
                        entity.rotationPitch = (float) ((double) entity.rotationPitch + rotation);
                    }
                }

                GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            for (int k = 0; k < j; ++k) {
                GlStateManager.pushMatrix();
                if (flag1) {
                    if (k > 0) {
                        float f7 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float f9 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        float f6 = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                        GlStateManager.translate(f7, f9, f6);
                    }

                    Minecraft.getMinecraft().getRenderItem().renderItem(itemstack, ibakedmodel);
                    GlStateManager.popMatrix();
                } else {
                    Minecraft.getMinecraft().getRenderItem().renderItem(itemstack, ibakedmodel);
                    GlStateManager.popMatrix();
                    GlStateManager.translate(0.0F, 0.0F, 0.05375F);
                }
            }

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            this.bindTexture(TextureMap.locationBlocksTexture);
            this.getRenderManager().renderEngine.getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
            return;
        }
        this.field_177079_e.setSeed(187L);
        boolean flag = false;

        if (this.bindEntityTexture(entity))
        {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            flag = true;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
        int i = this.func_177077_a(entity, x, y, z, partialTicks, ibakedmodel);

        for (int j = 0; j < i; ++j)
        {
            if (ibakedmodel.isGui3d())
            {
                GlStateManager.pushMatrix();

                if (j > 0)
                {
                    float f = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f1 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f2 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    GlStateManager.translate(f, f1, f2);
                }

                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
            }
            else
            {
                GlStateManager.pushMatrix();
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
                float f3 = ibakedmodel.getItemCameraTransforms().ground.scale.x;
                float f4 = ibakedmodel.getItemCameraTransforms().ground.scale.y;
                float f5 = ibakedmodel.getItemCameraTransforms().ground.scale.z;
                GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);

        if (flag)
        {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private int getModelCount(ItemStack stack) {
    int i = 1;
    if (stack.stackSize > 48) {
        i = 5;
    } else if (stack.stackSize > 32) {
        i = 4;
    } else if (stack.stackSize > 16) {
        i = 3;
    } else if (stack.stackSize > 1) {
        i = 2;
    }

    return i;
}

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityItem entity)
    {
        return TextureMap.locationBlocksTexture;
    }
}
