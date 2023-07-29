package top.fpsmaster.utils.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class PlayerUtil {
    public static void tellPlayerWithPrefix(String message) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\247b[FPSMaster] \247r" + message));
    }
}
