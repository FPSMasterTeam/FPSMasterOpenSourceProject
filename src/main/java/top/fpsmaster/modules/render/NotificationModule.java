package top.fpsmaster.modules.render;

import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.render.EventRender2D;

public class NotificationModule extends Module {
    public static BooleanValue showModuleToggle = new BooleanValue("ShowModule Toggle", true);

    public NotificationModule(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        addValues(showModuleToggle);
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        FPSMaster.INSTANCE.notificationsManager.draw();
    }

}
