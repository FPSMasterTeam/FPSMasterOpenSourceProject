package top.fpsmaster.modules.misc;

import net.minecraft.client.gui.ScaledResolution;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.misc.EventTick;
import top.fpsmaster.utils.math.TimerUtil;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class MemoryManager extends Module {
    private final BooleanValue autoGc = new BooleanValue("AutoGC", true);
    private final NumberValue<Number> gcLimit = new NumberValue<>("GCLimit", 60, 0, 100, 1);
    private final BooleanValue display = new BooleanValue("Display", false);
    public static BooleanValue fastLoad = new BooleanValue("FastLoad", true);

    public MemoryManager(String name, String desc) {
        super(name, desc, ModuleCategory.Misc);
        addValues(autoGc, gcLimit, fastLoad, display);
    }

    private final TimerUtil timer = new TimerUtil();
    private long maxMemory;
    private long usedMemory;
    private float pct;

    @Override
    public void onGui() {
        super.onGui();
        if (!this.display.getValue()) {
            return;
        }

        ScaledResolution sr = new ScaledResolution(mc);
        float rX = x * sr.getScaledWidth();
        float rY = y * sr.getScaledHeight();
        this.width = 150;
        this.height = 15;
        RenderUtil.drawRect(rX, rY, rX + 150, rY + 3, new Color(0, 0, 0, 120).getRGB());
        RenderUtil.drawRect(rX, rY, rX + 150 * this.pct / 100, rY + 3, FPSMaster.INSTANCE.theme.getPrimary());
        mc.fontRendererObj.drawStringWithShadow("Max:" + maxMemory / 1024 / 1024 + "m Used:" + usedMemory / 1024 / 1024 + "m  PCT:" + ((int) pct), rX, rY + 6, new Color(240, 240, 240).getRGB());
    }

    @EventTarget
    public void onTick(EventTick e) {
        if (autoGc.getValue()) {
            this.maxMemory = Runtime.getRuntime().maxMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            this.usedMemory = totalMemory - freeMemory;
            this.pct = (this.usedMemory * 100f / this.maxMemory);
            if (this.timer.delay(1000) && this.gcLimit.getValue().floatValue() <= this.pct) {
                Runtime.getRuntime().gc();
                //NotificationsUtils.sendMessage(NotificationType.INFO, " Memory Cleaned.");
                this.timer.reset();
            }

        }
    }
}
