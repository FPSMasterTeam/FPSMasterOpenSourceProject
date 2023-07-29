package me.guichaguri.betterfps;

import net.minecraft.client.Minecraft;

/**
 * Client event handling
 *
 * @author Guilherme Chaguri
 */
public class BetterFpsClient {
    protected static Minecraft mc;

    public static void start(Minecraft minecraft) {
        mc = minecraft;
        BetterFps.isClient = true;

        if (BetterFpsConfig.instance == null) {
            BetterFpsHelper.loadConfig();
        }
        BetterFpsHelper.init();
    }
}
