package top.fpsmaster.utils.minecraft;

import net.minecraft.client.Minecraft;

public class NetworkUtils {
    //judge whether the player is playing on hypixel.net
    public static boolean isOnHypixel() {
        return Minecraft.getMinecraft().theWorld != null && !Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("hypixel.net");
    }
}
