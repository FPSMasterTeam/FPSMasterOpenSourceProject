package top.fpsmaster.gui.keystrokes.keys.impl;

import net.minecraft.client.gui.Gui;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.AbstractKey;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeKey extends AbstractKey {
    private final SimpleDateFormat format;

    public TimeKey(KeyStrokes mod, int xOffset, int yOffset) {
        super(mod, xOffset, yOffset);
        this.format = new SimpleDateFormat("HH:mm:ss");
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

        if (!(((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showPing.getValue())) {
            yOffset -= 18;
        }
        int textColor = this.getColor();
        if ((((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).keyBackground.getValue())) {
            Gui.drawRect(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundColor.getColor()));
        }

        String text = format.format(new Date(System.currentTimeMillis()));
        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
            this.drawChromaString(text, x + (this.xOffset + 72) / 2 - this.mc.fontRendererObj.getStringWidth(text) / 2, y + yOffset + 4, 1.0D);
        } else {
            this.drawCenteredString(text, x + (this.xOffset + 72) / 2, y + yOffset + 4, textColor);
        }
    }
}
