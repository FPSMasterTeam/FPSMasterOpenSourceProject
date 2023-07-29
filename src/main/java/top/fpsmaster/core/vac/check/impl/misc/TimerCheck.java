package top.fpsmaster.core.vac.check.impl.misc;

import top.fpsmaster.core.managers.CheckManager;
import top.fpsmaster.core.vac.check.Check;

public class TimerCheck extends MiscCheck implements Check {
    private float prevTimerSpeed = mc.timer.timerSpeed;

    @Override
    public void execute(int type, Object... args) {
        if (type != CheckManager.TICK) return;
        while (mc.timer.timerSpeed != this.prevTimerSpeed) {
            mc.theWorld.sendQuittingDisconnectingPacket("\247c您已被\247bFPSMaster反作弊\247c踢出服务器！\n\n\2477原因: \247fTimer检测\n\2477官方群: \247b\247n928236434\n\n\2477Operated by \247fFPSMaster Team\2477.\n\2477如果这是误判，请及时保留这个截图并反馈！");
        }
        this.prevTimerSpeed = mc.timer.timerSpeed;
    }
}
