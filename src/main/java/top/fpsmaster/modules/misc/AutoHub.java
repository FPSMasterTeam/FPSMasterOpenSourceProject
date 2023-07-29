package top.fpsmaster.modules.misc;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.misc.EventTick;
import top.fpsmaster.utils.math.TimerUtil;

public class AutoHub extends Module {
    private final TimerUtil timer = new TimerUtil();
    private int lastHurtTick;

    public AutoHub(String name, String desc) {
        super(name, desc, ModuleCategory.Misc);
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (this.timer.hasReached(1000L)) {
            if (mc.thePlayer.getHealth() <= 3 || (mc.thePlayer.posY <= 10 && (mc.thePlayer.ticksExisted - this.lastHurtTick <= 200))) {
                mc.thePlayer.sendChatMessage("/hub");
                this.timer.reset();
            }
        }
        if (mc.thePlayer.hurtResistantTime == 20) {
            this.lastHurtTick = mc.thePlayer.ticksExisted;
        }
    }
}
