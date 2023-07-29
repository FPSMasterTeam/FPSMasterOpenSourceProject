package top.fpsmaster.modules.misc;

import net.minecraft.util.ScreenShotHelper;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.gui.notification.Notification;
import top.fpsmaster.gui.notification.info.Info;

public class AutoGG extends Module {
    public BooleanValue autoScreenShot = new BooleanValue("AutoScreenShot", true);
    public BooleanValue advertisement = new BooleanValue("Advertisement", false);

    public AutoGG(String name, String desc) {
        super(name, desc, ModuleCategory.Misc);
        this.addValues(autoScreenShot, advertisement);
    }

    public void sendGG() {
        if (this.stage) {
            mc.thePlayer.sendChatMessage("gg");
            FPSMaster.INSTANCE.notificationsManager.add(new Info("Auto sent GG", Notification.Type.Info));

            if (autoScreenShot.getValue()) {
                mc.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(mc.mcDataDir, mc.displayWidth, mc.displayHeight, mc.framebufferMc));
            }
            if (this.advertisement.getValue()) {
                new Thread(() -> {
                    try {
                        Thread.sleep(3000L);
                        mc.thePlayer.sendChatMessage("Lunar and Badlion is no longer the fashion, and it should be top.fpsmaster.FPSMaster");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
}
