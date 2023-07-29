package top.fpsmaster.modules.render;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.client.EventPacketReceive;
import top.fpsmaster.event.events.impl.player.EventMotion;
import top.fpsmaster.event.events.impl.render.EventRender3D;

public class TimeChanger extends Module {
    public NumberValue<Number> time = new NumberValue<>("Time", 14000, 0, 24000, 100);

    public TimeChanger(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        addValues(time);
    }



    @EventTarget
    public void onUpdate(EventMotion e) {
        if (mc.theWorld != null)
            mc.theWorld.setWorldTime(time.getValue().longValue());
    }

    @EventTarget
    public void onTimeChange(EventPacketReceive e){
        if(e.getPacket() instanceof S03PacketTimeUpdate){
            e.cancel = true;
        }
    }
}
