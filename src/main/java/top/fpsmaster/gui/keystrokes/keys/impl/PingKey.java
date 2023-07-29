package top.fpsmaster.gui.keystrokes.keys.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.AbstractKey;

public class PingKey extends AbstractKey {
    public PingKey(KeyStrokes mod, int xOffset, int yOffset) {
        super(mod, xOffset, yOffset);
    }

    public void renderKey(int x, int y) {
        int yOffset = this.yOffset;
        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showCPSOnButtons.getValue() || !(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showCPS.getValue())) {
            yOffset -= 18;
        }

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showSpacebar.getValue())) {
            yOffset -= 18;
        }

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showSneak.getValue())) {
            yOffset -= 18;
        }

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showMouseButtons.getValue())) {
            yOffset -= 24;
        }

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showWASD.getValue())) {
            yOffset -= 48;
        }

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showFPS.getValue())) {
            yOffset -= 18;
        }

        int textColor = this.getColor();
        if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).keyBackground.getValue())) {
            Gui.drawRect(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundColor.getColor()));
        }

        NetHandlerPlayClient netHandler = Minecraft.getMinecraft().getNetHandler();
        String ping = null;
        if (netHandler != null) {
            NetworkPlayerInfo playerInfo = netHandler.getPlayerInfo(Minecraft.getMinecraft().getSession().getProfile().getId());
            if (playerInfo != null) {
                ping = Integer.toString(playerInfo.getResponseTime());
            }
        }

        String text = mc.isSingleplayer() ? "SinglePlayer" : (ping == null ? "Unknown" : ping + "ms");
        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
            this.drawChromaString(text, x + (this.xOffset + 72) / 2 - this.mc.fontRendererObjWithoutUnicode.getStringWidth(text) / 2, y + yOffset + 4, 1.0D);
        } else {
            this.drawCenteredString(text, x + (this.xOffset + 72) / 2, y + yOffset + 4, textColor);
        }

    }
}
