package top.fpsmaster.core.vac.check.impl.combat;

import top.fpsmaster.core.managers.CheckManager;
import top.fpsmaster.core.vac.check.Check;
import top.fpsmaster.utils.Utils;
import top.fpsmaster.utils.math.MouseButton;
import top.fpsmaster.utils.math.TimerUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AutoClickerCheck extends CombatCheck implements Check {
    private final HashMap<MouseButton, Long> leftHashMap = new HashMap<>();
    private final ArrayList<Long> delayList = new ArrayList<>();
    private final TimerUtil leftCounter = new TimerUtil();
    private long lastLeftTime = 0;

    @Override
    public void execute(int type, Object... args) {
        if (args.length == 0 && type == CheckManager.LEFT_CLICK) {
            boolean active = Utils.cpsUtil.getLeftCps() < 10;
            long clickTime = System.currentTimeMillis();
            leftHashMap.put(new MouseButton(clickTime), this.lastLeftTime);
            this.lastLeftTime = clickTime;
            if (leftHashMap.size() >= 18 && leftCounter.delay(1000.0f)) {
                for (Map.Entry<MouseButton, Long> entry : this.leftHashMap.entrySet()) {
                    this.delayList.add(entry.getKey().getLastMs() - entry.getValue());
                }
                boolean sameDelay = true;
                long prevDelay = this.delayList.get(0);
                for (Object delayObject : Arrays.copyOfRange(this.delayList.toArray(), 1, this.delayList.size())) {
                    long delay = (long) delayObject;
                    boolean tempSame = false;
                    for (int i = 10 / -Utils.cpsUtil.getLeftCps(); i <= 10 / Utils.cpsUtil.getLeftCps(); i++) {
                        if (delay == (prevDelay + i)) {
                            tempSame = true;
                            break;
                        }
                    }
                    prevDelay = delay;
                    if (!tempSame) {
                        sameDelay = false;
                        break;
                    }
                }
                if (sameDelay && active) {
                    mc.ingameGUI.displayingCheating = true;
                    mc.ingameGUI.displayTitle("\247b请勿使用任何的作弊程序！！！", null, 0, 0, 0);
                    mc.ingameGUI.displayTitle(null, "\247e检测到使用: \2474AutoClicker", 0, 0, 0);
                    mc.ingameGUI.displayTitle(null, null, 0, 100, 10);
                    if (this.vl >= 5 && this.vl % 2 == 1) {
                        mc.theWorld.sendQuittingDisconnectingPacket("\247c您已被\247bFPSMaster反作弊\247c踢出服务器！\n\n\2477原因: \247fAutoClicker检测\n\2477官方群: \247b\247n928236434\n\n\2477Operated by \247fFPSMaster Team\2477.\n\2477如果这是误判，请及时保留这个截图并反馈！");
                    }
                }
                this.leftHashMap.clear();
                this.delayList.clear();
                this.leftCounter.reset();
            }
        }
    }
}
