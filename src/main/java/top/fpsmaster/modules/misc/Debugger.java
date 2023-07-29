package top.fpsmaster.modules.misc;

import net.minecraft.entity.player.EntityPlayer;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.player.EventMotion;

public class Debugger extends Module {
    public Debugger(String name, String desc) {
        super(name, desc, ModuleCategory.Misc);
    }

    @EventTarget
    public void onUpdate(EventMotion eventTick) {
        if (mc.thePlayer.ticksExisted % 20 == 0) {
            for (EntityPlayer e : mc.theWorld.playerEntities) {
                System.out.println("Player info -> " + e.getName());
            }
        }
    }
}
