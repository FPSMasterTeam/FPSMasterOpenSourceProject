package net.minecraft.client.renderer;

import top.fpsmaster.modules.render.OldAnimation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.optifine.DynamicLights;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");

    /**
     * A reference to the Minecraft object.
     */
    private final Minecraft mc;
    private ItemStack itemToRender;

    /**
     * How far the current item has been equipped (0 disequipped and 1 fully up)
     */
    private float equippedProgress;
    private float prevEquippedProgress;
    private final RenderManager renderManager;
    private final RenderItem itemRenderer;

    /**
     * The index of the currently held item (0-8, or -1 if not yet updated)
     */
    private int equippedItemSlot = -1;

    public ItemRenderer(Minecraft mcIn) {
        this.mc = mcIn;
        this.renderManager = mcIn.getRenderManager();
        this.itemRenderer = mcIn.getRenderItem();
    }

    public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
        if (heldStack != null) {
            Item item = heldStack.getItem();
            Block block = Block.getBlockFromItem(item);
            GlStateManager.pushMatrix();

            if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
                GlStateManager.scale(2.0F, 2.0F, 2.0F);

                if (this.isBlockTranslucent(block) && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)) {
                    GlStateManager.depthMask(false);
                }
            }

            this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);

            if (this.isBlockTranslucent(block)) {
                GlStateManager.depthMask(true);
            }

            GlStateManager.popMatrix();
        }
    }

    /**
     * Returns true if given block is translucent
     */
    private boolean isBlockTranslucent(Block blockIn) {
        return blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
    }

    /**
     * Rotate the render around X and Y
     *
     * @param angleY The angle for the rotation arround Y
     */
    private void rotateArroundXAndY(float angle, float angleY) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(angleY, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    /**
     * Set the OpenGL LightMapTextureCoords based on the AbstractClientPlayer
     */
    private void setLightMapFromPlayer(AbstractClientPlayer clientPlayer) {
        int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + (double) clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);

        if (Config.isDynamicLights()) {
            i = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), i);
        }

        float f = (float) (i & 65535);
        float f1 = (float) (i >> 16);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
    }

    /**
     * Rotate the render according to the player's yaw and pitch
     */
    private void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks) {
        float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
        float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
        GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
    }

    /**
     * Return the angle to render the Map
     *
     * @param pitch The player's pitch
     */
    private float getMapAngleFromPitch(float pitch) {
        float f = 1.0F - pitch / 45.0F + 0.1F;
        f = MathHelper.clamp_float(f, 0.0F, 1.0F);
        f = -MathHelper.cos(f * (float) Math.PI) * 0.5F + 0.5F;
        return f;
    }

    private void renderRightArm(RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(0.25F, -0.85F, 0.75F);
        renderPlayerIn.renderRightArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void renderLeftArm(RenderPlayer renderPlayerIn) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-0.3F, -1.1F, 0.45F);
        renderPlayerIn.renderLeftArm(this.mc.thePlayer);
        GlStateManager.popMatrix();
    }

    private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        RenderPlayer renderplayer = (RenderPlayer) render;

        if (!clientPlayer.isInvisible()) {
            GlStateManager.disableCull();
            this.renderRightArm(renderplayer);
            this.renderLeftArm(renderplayer);
            GlStateManager.enableCull();
        }
    }

    private void renderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress) {
        float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0F);
        float f2 = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
        GlStateManager.translate(f, f1, f2);
        float f3 = this.getMapAngleFromPitch(pitch);
        GlStateManager.translate(0.0F, 0.04F, -0.72F);
        GlStateManager.translate(0.0F, equipmentProgress * -1.2F, 0.0F);
        GlStateManager.translate(0.0F, f3 * -0.5F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * -85.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        this.renderPlayerArms(clientPlayer);
        float f4 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f5 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f4 * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f5 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f5 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.38F, 0.38F, 0.38F);
        GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-1.0F, -1.0F, 0.0F);
        GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
        this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GL11.glNormal3f(0.0F, 0.0F, -1.0F);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
        worldrenderer.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
        worldrenderer.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
        worldrenderer.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        MapData mapdata = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);

        if (mapdata != null) {
            this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
        }
    }

    /**
     * Render the player's arm
     *
     * @param equipProgress The progress of equiping the item
     * @param swingProgress The swing movement progression
     */
    private void renderPlayerArm(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress) {
        float f = -0.3F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        float f1 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0F);
        float f2 = -0.4F * MathHelper.sin(swingProgress * (float) Math.PI);
        GlStateManager.translate(f, f1, f2);
        GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
        GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f4 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f4 * 70.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        GlStateManager.translate(-1.0F, 3.6F, 3.5F);
        GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F);
        GlStateManager.translate(5.6F, 0.0F, 0.0F);
        Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
        GlStateManager.disableCull();
        RenderPlayer renderplayer = (RenderPlayer) render;
        renderplayer.renderRightArm(this.mc.thePlayer);
        GlStateManager.enableCull();
    }

    /**
     * Rotate and translate render to show item consumption
     *
     * @param swingProgress The swing movement progress
     */
    private void doItemUsedTransformations(float swingProgress) {
        float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI * 2.0F);
        float f2 = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
        GlStateManager.translate(f, f1, f2);
    }

    /**
     * Perform the drinking animation movement
     *
     * @param partialTicks Partials ticks
     */
    private void performDrinking(AbstractClientPlayer clientPlayer, float partialTicks) {
        float f = (float) clientPlayer.getItemInUseCount() - partialTicks + 1.0F;
        float f1 = f / (float) this.itemToRender.getMaxItemUseDuration();
        float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * (float) Math.PI) * 0.1F);

        if (f1 >= 0.8F) {
            f2 = 0.0F;
        }

        GlStateManager.translate(0.0F, f2, 0.0F);
        float f3 = 1.0F - (float) Math.pow(f1, 27.0D);
        GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
        GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
    }

    /**
     * Performs transformations prior to the rendering of a held item in first person.
     */
    private void transformFirstPersonItem(float equipProgress, float swingProgress) {
        GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
        GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        float f = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
        float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.4F, 0.4F, 0.4F);
    }

    /**
     * Translate and rotate the render to look like holding a bow
     *
     * @param partialTicks Partial ticks
     */
    private void doBowTransformations(float partialTicks, AbstractClientPlayer clientPlayer) {
        GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.translate(-0.9F, 0.2F, 0.0F);
        float f = (float) this.itemToRender.getMaxItemUseDuration() - ((float) clientPlayer.getItemInUseCount() - partialTicks + 1.0F);
        float f1 = f / 20.0F;
        f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;

        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        if (f1 > 0.1F) {
            float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
            float f3 = f1 - 0.1F;
            float f4 = f2 * f3;
            GlStateManager.translate(f4 * 0.0F, f4 * 0.01F, f4 * 0.0F);
        }

        GlStateManager.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.1F);
        GlStateManager.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
    }

    /**
     * Translate and rotate the render for holding a block
     */
    private void doBlockTransformations() {
        GlStateManager.translate(-0.5F, 0.2F, 0.0F);
        GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
    }

    /**
     * Renders the active item in the player's hand when in first person mode. Args: partialTickTime
     */
    public void renderItemInFirstPerson(float partialTicks) {
        if (!Config.isShaders() || !Shaders.isSkipRenderHand()) {
            float f = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
            float f1 = this.mc.thePlayer.getSwingProgress(partialTicks);
            float f2 = this.mc.thePlayer.prevRotationPitch + (this.mc.thePlayer.rotationPitch - this.mc.thePlayer.prevRotationPitch) * partialTicks;
            float f3 = this.mc.thePlayer.prevRotationYaw + (this.mc.thePlayer.rotationYaw - this.mc.thePlayer.prevRotationYaw) * partialTicks;
            this.rotateArroundXAndY(f2, f3);
            this.setLightMapFromPlayer(this.mc.thePlayer);
            this.rotateWithPlayerRotations(this.mc.thePlayer, partialTicks);
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();

            if (this.itemToRender != null) {
                if (OldAnimation.oldRod.getValue() && itemToRender.getItem() instanceof ItemCarrotOnAStick) {
                    GlStateManager.translate(0.08F, -0.027F, -0.33F);
                    GlStateManager.scale(0.93F, 1.0F, 1.0F);
                }
                if (OldAnimation.oldRod.getValue() && itemToRender.getItem() instanceof ItemFishingRod) {
                    GlStateManager.translate(0.08F, -0.027F, -0.33F);
                    GlStateManager.scale(0.93F, 1.0F, 1.0F);
                }
                if (OldAnimation.oldSwing.getValue() && f1 != 0.0F && !mc.thePlayer.isBlocking() && !mc.thePlayer.isEating() && !mc.thePlayer.isUsingItem()) {
                    GlStateManager.scale(0.85F, 0.85F, 0.85F);
                    GlStateManager.translate(-0.06F, 0.003F, 0.05F);
                }
                if (this.itemToRender.getItem() instanceof ItemMap) {
                    this.renderItemMap(this.mc.thePlayer, f2, f, f1);
                } else if (this.mc.thePlayer.getItemInUseCount() > 0) {
                    EnumAction enumaction = this.itemToRender.getItemUseAction();
                    switch (enumaction) {
                        case NONE:
                            this.transformFirstPersonItem(f, 0.0F);
                            break;
                        case EAT:
                        case DRINK:
                            this.performDrinking(this.mc.thePlayer, partialTicks);
                            this.transformFirstPersonItem(f, 0.0F);
                            break;

                        case BLOCK:
                            if (OldAnimation.blockHit.getValue()) {
                                this.transformFirstPersonItem(f, f1);
                            } else {
                                this.transformFirstPersonItem(f, 0.0F);
                            }
                            this.doBlockTransformations();
                            break;

                        case BOW:
                            if (OldAnimation.oldBow.getValue()) {
                                this.transformFirstPersonItem(f, f1);
                            } else {
                                this.transformFirstPersonItem(f, 0.0F);
                            }
                            this.doBowTransformations(partialTicks, this.mc.thePlayer);
                    }
                } else {
                    this.doItemUsedTransformations(f1);
                    this.transformFirstPersonItem(f, f1);
                }

                this.renderItem(this.mc.thePlayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
            } else if (!this.mc.thePlayer.isInvisible()) {
                this.renderPlayerArm(this.mc.thePlayer, f, f1);
            }

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            RenderHelper.disableStandardItemLighting();
        }
    }

    /**
     * Renders all the overlays that are in first person mode. Args: partialTickTime
     */
    public void renderOverlays(float partialTicks) {
        GlStateManager.disableAlpha();

        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
            BlockPos blockpos = new BlockPos(this.mc.thePlayer);
            EntityPlayer entityplayer = this.mc.thePlayer;

            for (int i = 0; i < 8; ++i) {
                double d0 = entityplayer.posX + (double) (((float) (i % 2) - 0.5F) * entityplayer.width * 0.8F);
                double d1 = entityplayer.posY + (double) (((float) ((i >> 1) % 2) - 0.5F) * 0.1F);
                double d2 = entityplayer.posZ + (double) (((float) ((i >> 2) % 2) - 0.5F) * entityplayer.width * 0.8F);
                BlockPos blockpos1 = new BlockPos(d0, d1 + (double) entityplayer.getEyeHeight(), d2);
                IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos1);

                if (iblockstate1.getBlock().isVisuallyOpaque()) {
                    iblockstate = iblockstate1;
                    blockpos = blockpos1;
                }
            }

            if (iblockstate.getBlock().getRenderType() != -1) {
                Object object = Reflector.getFieldValue(Reflector.RenderBlockOverlayEvent_OverlayType_BLOCK);

                if (!Reflector.callBoolean(Reflector.ForgeEventFactory_renderBlockOverlay, this.mc.thePlayer, partialTicks, object, iblockstate, blockpos)) {
                    this.renderBlockInHand(this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
                }
            }
        }

        if (!this.mc.thePlayer.isSpectator()) {
            if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderWaterOverlay, this.mc.thePlayer, partialTicks)) {
                this.renderWaterOverlayTexture(partialTicks);
            }

            if (this.mc.thePlayer.isBurning() && !Reflector.callBoolean(Reflector.ForgeEventFactory_renderFireOverlay, this.mc.thePlayer, partialTicks)) {
                this.renderFireInFirstPerson();
            }
        }

        GlStateManager.enableAlpha();
    }

    /**
     * Render the block in the player's hand
     *
     * @param atlas        The TextureAtlasSprite to render
     */
    private void renderBlockInHand(TextureAtlasSprite atlas) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
        GlStateManager.pushMatrix();
        float f = atlas.getMinU();
        float f1 = atlas.getMaxU();
        float f2 = atlas.getMinV();
        float f3 = atlas.getMaxV();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex(f1, f3).endVertex();
        worldrenderer.pos(1.0D, -1.0D, -0.5D).tex(f, f3).endVertex();
        worldrenderer.pos(1.0D, 1.0D, -0.5D).tex(f, f2).endVertex();
        worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex(f1, f2).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders a texture that warps around based on the direction the player is looking. Texture needs to be bound
     * before being called. Used for the water overlay. Args: parialTickTime
     *
     * @param partialTicks Partial ticks
     */
    private void renderWaterOverlayTexture(float partialTicks) {
        if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
            this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            float f = this.mc.thePlayer.getBrightness(partialTicks);
            GlStateManager.color(f, f, f, 0.5F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            float f1 = -this.mc.thePlayer.rotationYaw / 64.0F;
            float f2 = this.mc.thePlayer.rotationPitch / 64.0F;
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex(4.0F + f1, 4.0F + f2).endVertex();
            worldrenderer.pos(1.0D, -1.0D, -0.5D).tex(0.0F + f1, 4.0F + f2).endVertex();
            worldrenderer.pos(1.0D, 1.0D, -0.5D).tex(0.0F + f1, 0.0F + f2).endVertex();
            worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex(4.0F + f1, 0.0F + f2).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
        }
    }

    /**
     * Renders the fire on the screen for first person mode. Arg: partialTickTime
     *
     */
    private void renderFireInFirstPerson() {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        float f = 1.0F;
        for (int i = 0; i < 2; ++i) {
            GlStateManager.pushMatrix();
            TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            float f1 = textureatlassprite.getMinU();
            float f2 = textureatlassprite.getMaxU();
            float f3 = textureatlassprite.getMinV();
            float f4 = textureatlassprite.getMaxV();
            float f5 = (0.0F - f) / 2.0F;
            float f6 = f5 + f;
            float f7 = 0.0F - f / 2.0F;
            float f8 = f7 + f;
            float f9 = -0.5F;
            GlStateManager.translate((float) (-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GlStateManager.rotate((float) (i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.setSprite(textureatlassprite);
            worldrenderer.pos(f5, f7, f9).tex(f2, f4).endVertex();
            worldrenderer.pos(f6, f7, f9).tex(f1, f4).endVertex();
            worldrenderer.pos(f6, f8, f9).tex(f1, f3).endVertex();
            worldrenderer.pos(f5, f8, f9).tex(f2, f3).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }

    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        EntityPlayer entityplayer = this.mc.thePlayer;
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        boolean flag = false;

        if (this.itemToRender != null && itemstack != null) {
            if (!this.itemToRender.getIsItemStackEqual(itemstack)) {
                if (Reflector.ForgeItem_shouldCauseReequipAnimation.exists()) {
                    boolean flag1 = Reflector.callBoolean(this.itemToRender.getItem(), Reflector.ForgeItem_shouldCauseReequipAnimation, this.itemToRender, itemstack, this.equippedItemSlot != entityplayer.inventory.currentItem);

                    if (!flag1) {
                        this.itemToRender = itemstack;
                        this.equippedItemSlot = entityplayer.inventory.currentItem;
                        return;
                    }
                }

                flag = true;
            }
        } else flag = this.itemToRender != null || itemstack != null;

        float f2 = 0.4F;
        float f = flag ? 0.0F : 1.0F;
        float f1 = MathHelper.clamp_float(f - this.equippedProgress, -f2, f2);
        this.equippedProgress += f1;

        if (this.equippedProgress < 0.1F) {
            this.itemToRender = itemstack;
            this.equippedItemSlot = entityplayer.inventory.currentItem;

            if (Config.isShaders()) {
                Shaders.setItemToRenderMain(itemstack);
            }
        }
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0F;
    }

    /**
     * Resets equippedProgress
     */
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0F;
    }
}
