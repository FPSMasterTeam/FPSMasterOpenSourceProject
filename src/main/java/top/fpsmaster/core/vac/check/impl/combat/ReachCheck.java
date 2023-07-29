package top.fpsmaster.core.vac.check.impl.combat;

import net.minecraft.world.WorldSettings;
import top.fpsmaster.core.managers.CheckManager;
import top.fpsmaster.core.vac.check.Check;

public class ReachCheck extends CombatCheck implements Check {
    @Override
    public void execute(int type, Object... args) {
        if (type != CheckManager.LEFT_CLICK || getTargetPlayer() == null) return;
        if (mc.playerController.getCurrentGameType() != WorldSettings.GameType.SURVIVAL && mc.playerController.getCurrentGameType() != WorldSettings.GameType.ADVENTURE)
            return;
        if (getTargetPlayer() != null) {
            if (getTargetPlayer().getRange() >= 3.02) {
                this.vl++;
                mc.ingameGUI.displayingCheating = true;
                mc.ingameGUI.displayTitle("\247b请勿使用任何的作弊程序！！！", null, 0, 0, 1);
                mc.ingameGUI.displayTitle(null, "\247e检测到使用: \2474Reach", 0, 0, 1);
                mc.ingameGUI.displayTitle(null, null, 0, 100, 10);
                if (this.vl >= 5 && this.vl % 2 == 0) {
                    mc.theWorld.sendQuittingDisconnectingPacket("\247c您已被\247bFPSMaster反作弊\247c踢出服务器！\n\n\2477原因: \247fReach检测\n\2477官方群: \247b\247n928236434\n\n\2477Operated by \247fFPSMaster Team\2477.\n\2477如果这是误判，请及时保留这个截图并反馈！");
                }
            }
        }
    }
}
