package top.fpsmaster.modules.render;

import org.lwjgl.input.Keyboard;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.render.EventShader;
import top.fpsmaster.gui.notification.Notification;
import top.fpsmaster.gui.notification.info.Info;
import top.fpsmaster.utils.render.RoundedUtil;

import java.awt.*;

public class ClickGui extends Module {
    private final BooleanValue pauseGame = new BooleanValue("PauseGame", true);

    public ClickGui(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        this.addValues(this.pauseGame);
        if (key == 0) key = Keyboard.KEY_RSHIFT;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.currentScreen == null) mc.displayGuiScreen(new top.fpsmaster.gui.ClickGui(this.pauseGame.getValue()));
        FPSMaster.INSTANCE.notificationsManager.add(new Info("This ClickGui is Still under Developing, It May be Astable.", Notification.Type.Info));
    }

    @EventTarget
    public void onShader(EventShader e) {

        RoundedUtil.drawRound(top.fpsmaster.gui.ClickGui.Companion.getX() + 1, top.fpsmaster.gui.ClickGui.Companion.getY() + 1, top.fpsmaster.gui.ClickGui.Companion.getWidth() - 2, top.fpsmaster.gui.ClickGui.Companion.getHeight() - 2, 5, false, new Color(255, 255, 255));

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
