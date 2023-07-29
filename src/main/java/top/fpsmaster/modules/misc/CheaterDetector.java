package top.fpsmaster.modules.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.misc.EventTick;
import top.fpsmaster.event.events.impl.vac.EventDamage;
import top.fpsmaster.utils.minecraft.PlayerUtil;
import top.fpsmaster.utils.special.vac.CheatDetection;

import java.util.stream.Collectors;

public class CheaterDetector extends Module {
    private final BooleanValue verbose = new BooleanValue("Verbose", false);
    public final BooleanValue mark = new BooleanValue("Mark", false);

    public CheaterDetector(String name, String desc) {
        super(name, desc, ModuleCategory.Misc);
        this.addValues(this.verbose, this.mark);
    }

    @EventTarget
    public void onTick(EventTick e) {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer && entity != mc.thePlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getSpeed() >= player.getBaseMoveSpeed() * 0.93 && (player.moveForward < 0.0F || player.moveForward == 0.0F && player.moveStrafing != 0.0F)) {
                    CheatDetection detection = CheatDetection.get(player.getUniqueID());
                    detection.sprintPercentage += 20;
                    if (detection.sprintPercentage >= 100) {
                        detection.sprint++;
                        detection.hacks = true;
                        detection.sprintPercentage = 0;
                        PlayerUtil.tellPlayerWithPrefix("\247f" + player.getName() + "\2477 failed \247cSprint check\2477(VL:" + detection.sprint + ")");
                    } else if (verbose.getValue()) {
                        PlayerUtil.tellPlayerWithPrefix("\247f" + player.getName() + "\2477 seemed to fail \247cSprint check\2477(" + detection.sprintPercentage + "%)");
                    }
                }
                if (player.isUsingItem() && player.onGround && player.hurtResistantTime == 0 && !player.isPotionActive(Potion.moveSpeed) && player.getSpeed() >= player.getBaseMoveSpeed() * 0.9 && !player.getHeldItem().getDisplayName().toLowerCase().contains("bow")) {
                    CheatDetection detection = CheatDetection.get(player.getUniqueID());
                    detection.noSlowPercentage += 20;
                    if (detection.noSlowPercentage >= 100) {
                        detection.noSlow++;
                        detection.hacks = true;
                        detection.noSlowPercentage = 0;
                        PlayerUtil.tellPlayerWithPrefix("\247f" + player.getName() + "\2477 failed \247cNoSlow check\2477(VL:" + detection.noSlow + ")");
                    } else if (verbose.getValue()) {
                        PlayerUtil.tellPlayerWithPrefix("\247f" + player.getName() + "\2477 seemed to fail \247cNoSlow check\2477(" + detection.noSlowPercentage + "%)");
                    }
                }
            }
        }
    }

    @EventTarget
    public void onDamage(EventDamage e) {
        if (mc.thePlayer.fire != -20 || mc.thePlayer.isImmuneToExplosions()) return;
        if (e.getDamageSource() == DamageSource.generic && !e.getDamageSource().isFireDamage() && !e.getDamageSource().isMagicDamage() && e.getDamageSource().isUnblockable() && !e.getDamageSource().isExplosion() && !e.getDamageSource().isProjectile()) {
            int i = 0;
            EntityPlayer entityPlayer = null;
            boolean player = false;
            for (Entity entity : mc.theWorld.loadedEntityList.stream().filter(entity -> entity instanceof EntityPlayer && entity != mc.thePlayer && entity.getDistanceToEntity(mc.thePlayer) <= 6.02).collect(Collectors.toList())) {
                i++;
                entityPlayer = (EntityPlayer) entity;
                if (this.reachCheck(entity, (((EntityPlayer) entity).isPotionActive(Potion.moveSpeed) ? (0.5 * (((EntityPlayer) entity).getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) + 5.0 / (((EntityPlayer) entity).getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1)) * 0.2 : 0.0) + (((EntityPlayer) entity).getSpeed() / 0.2873 * 0.5) + (mc.thePlayer.attackedOther ? 0.0 : 0.0625))) {
                    return;
                }
                player = true;
            }
            if (i == 1) {
                if (entityPlayer.getItemInUse() != null && (entityPlayer.getItemInUse().getItem() instanceof ItemBow || entityPlayer.getItemInUse().getItem() instanceof ItemFishingRod)) {
                    return;
                }
                CheatDetection detection = CheatDetection.get(entityPlayer.getUniqueID());
                detection.reachPercentage += 20;
                if (detection.reachPercentage >= 100) {
                    detection.reach++;
                    detection.hacks = true;
                    detection.reachPercentage = 0;
                    PlayerUtil.tellPlayerWithPrefix("\247f" + entityPlayer.getName() + "\2477 failed \247cReach check\2477(VL:" + detection.reach + ")");
                } else if (verbose.getValue()) {
                    PlayerUtil.tellPlayerWithPrefix("\247f" + entityPlayer.getName() + "\2477 seemed to fail \247cReach check\2477(" + detection.reachPercentage + "%)");
                }
            } else if (player) {
                PlayerUtil.tellPlayerWithPrefix("\247fThe person attacked you\2477 might use \247cReach");
            }
        }
    }

    private boolean reachCheck(Entity entity, double addition) {
        if (mc.thePlayer.posY > entity.getPositionEyes(mc.timer.renderPartialTicks).yCoord) {
            return entity.getPositionEyes(mc.timer.renderPartialTicks).distanceTo(mc.thePlayer.getPositionVector()) <= 3.72 + addition;
        } else if (mc.thePlayer.posY + 1.8 < entity.getPositionEyes(mc.timer.renderPartialTicks).yCoord) {
            return entity.getPositionEyes(mc.timer.renderPartialTicks).distanceTo(mc.thePlayer.getPositionVector().addVector(0.0D, 1.8D, 0.0D)) <= 3.78 + addition;
        } else {
            return entity.getPositionEyes(mc.timer.renderPartialTicks).distanceTo(mc.thePlayer.getPositionVector().addVector(0.0D, 0.9D, 0.0D)) <= 3.46 + addition;
        }
    }
}
