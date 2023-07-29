package top.fpsmaster.core.vac.check;

import net.minecraft.client.Minecraft;

public interface Check {
    Minecraft mc = Minecraft.getMinecraft();

    void execute(int type, Object... args);
}
