package top.fpsmaster.utils.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import top.fpsmaster.core.I18N.I18NUtils;

public class NotificationsUtils {
    public static void sendMessage(NotificationType type, String content) {
        switch (type) {
            case INFO:
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(I18NUtils.getGlobal(ChatFormatting.BLUE + "[INFO]:" + ChatFormatting.GRAY + content)));
                break;
            case MESSAGE:
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(I18NUtils.getGlobal(ChatFormatting.WHITE + "[MESSAGE]:" + ChatFormatting.GRAY + content)));
                break;
            case ERROR:
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(I18NUtils.getGlobal(ChatFormatting.DARK_RED + "[ERROR]:" + ChatFormatting.GRAY + content)));
                break;
            case WARNING:
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(I18NUtils.getGlobal(ChatFormatting.RED + "[WARNING]:" + ChatFormatting.GRAY + content)));
                break;
            default:
                break;
        }
    }


}
