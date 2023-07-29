package top.fpsmaster.core.vac.check.impl.misc;

import top.fpsmaster.core.managers.CheckManager;
import top.fpsmaster.core.vac.check.Check;

import java.util.Map;

public class ByteCheck extends MiscCheck implements Check {
    @Override
    public void execute(int type, Object... args) {
        if (type != CheckManager.TICK) return;
        int i = 0;
        for (Map.Entry<String, byte[]> entry : getValue().entrySet()) {
            if (entry.getKey().equals("EntityPlayerSP")) {
                i = 1;
                continue;
            }
            if (entry.getKey().equals("Minecraft")) {
                i = 2;
                continue;
            }
            if (entry.getKey().toLowerCase().startsWith("_")) {
                if (i == 1) {
                    if (entry.getKey().equalsIgnoreCase("_EntityPlayerSelfPlayer")) {
                        if (entry.getValue() != getValue().get("EntityPlayerSP")) {
                            mc.theWorld.sendQuittingDisconnectingPacket("\247c您已被\247bFPSMaster反作弊\247c踢出服务器！\n\n\2477原因: \247f非法注入检测\n\2477官方群: \247b\247n928236434\n\n\2477Operated by \247fFPSMaster Team\2477.\n\2477如果这是误判，请及时保留这个截图并反馈！");
                        }
                    }
                }
            }
        }
    }
}
