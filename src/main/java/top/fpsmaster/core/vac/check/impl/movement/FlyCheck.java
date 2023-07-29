package top.fpsmaster.core.vac.check.impl.movement;

import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.world.WorldSettings;
import top.fpsmaster.core.managers.CheckManager;
import top.fpsmaster.core.vac.check.Check;

public class FlyCheck extends MoveCheck implements Check {
    private double prevMotionY;
    public S39PacketPlayerAbilities lastReceivedCapabilityPacket = null;

    @Override
    public void execute(int type, Object... args) {
        if (type == CheckManager.UPDATE_WALKING) {
            if (mc.thePlayer.capabilities.isCreativeMode || mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE || mc.thePlayer.isSpectator()) {
                return;
            }
            if (lastReceivedCapabilityPacket.isAllowFlying()) return;
            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.1, 0.0D)).isEmpty()) {
                if (mc.thePlayer.capabilities.isFlying) {
                    if (this.lastReceivedCapabilityPacket == null) {
                        if (!mc.thePlayer.capabilities.allowFlying) {
                            mc.thePlayer.capabilities.isFlying = false;
                            pushUpVl();
                        }
                    } else {
                        if (lastReceivedCapabilityPacket.isAllowFlying()) return;
                        mc.thePlayer.capabilities.isFlying = lastReceivedCapabilityPacket.isFlying();
                    }
                } else if (mc.thePlayer.motionY == this.prevMotionY) {
                    if (this.prevMotionY == -0.1D) {
                        mc.thePlayer.motionY = -Math.PI;
                        pushUpVl();
                    }
                }
            }
            this.prevMotionY = mc.thePlayer.motionY;
        }
    }

    private void pushUpVl() {
        this.vl++;
        mc.ingameGUI.displayingCheating = true;
        mc.ingameGUI.displayTitle("\247b请勿使用任何的作弊程序！！！", null, 0, 0, 0);
        mc.ingameGUI.displayTitle(null, "\247e检测到使用: \2474飞行", 0, 0, 0);
        mc.ingameGUI.displayTitle(null, null, 0, 100, 10);
        if (this.vl >= 5 && this.vl % 2 == 1) {
            mc.theWorld.sendQuittingDisconnectingPacket("\247c您已被\247bFPSMaster反作弊\247c踢出服务器！\n\n\2477原因: \247fFly检测\n\2477官方群: \247b\247n928236434\n\n\2477Operated by \247fFPSMaster Team\2477.\n\2477如果这是误判，请及时保留这个截图并反馈！");
        }
    }
}
