package top.fpsmaster.gui.keystrokes.keys.impl;

import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.event.EventManager;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.client.EventKeyInput;
import top.fpsmaster.event.events.impl.client.EventMouseInput;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.AbstractKey;

import java.util.ArrayList;
import java.util.List;

public class CPSKey extends AbstractKey {
    private final List<Long> leftClicks = new ArrayList<>();
    private final List<Long> rightClicks = new ArrayList<>();

    public CPSKey(KeyStrokes mod, int xOffset, int yOffset) {
        super(mod, xOffset, yOffset);
        EventManager.register(this);
    }

    public void renderKey(int x, int y) {
        int yOffset = this.yOffset;
        if (!((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showMouseButtons.getValue()) {
            yOffset -= 24;
        }

        if (!((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showSpacebar.getValue()) {
            yOffset -= 18;
        }

        if (!((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showSneak.getValue()) {
            yOffset -= 18;
        }

        if (!((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showWASD.getValue()) {
            yOffset -= 48;
        }

        Mouse.poll();
        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).keyBackground.getValue()) {
            Gui.drawRect(x + this.xOffset, y + yOffset, x + this.xOffset + 70, y + yOffset + 16, ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).backgroundColor.getColor());
        }

        String name = this.getLeftCPS() + " CPS";
        int textX = x + (this.xOffset + 70) / 2;
        int textY = y + yOffset + 4;
        if (((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).chroma.getValue()) {
            this.drawChromaString(name, textX - this.mc.fontRendererObj.getStringWidth(name) / 2, textY, 1.0D);
        } else {
            this.drawCenteredString(name, textX, textY, this.getColor());
        }

    }

    int getLeftCPS() {
        long time = System.currentTimeMillis();
        this.leftClicks.removeIf((o) -> o + 1000L < time);
        return this.leftClicks.size();
    }

    int getRightCPS() {
        long time = System.currentTimeMillis();
        this.rightClicks.removeIf((o) -> o + 1000L < time);
        return this.rightClicks.size();
    }

    @EventTarget
    public void onMouseInput(EventMouseInput event) {
        if (event.getType() == 0) {
            this.leftClicks.add(System.currentTimeMillis());
        } else if (event.getType() == 1) {
            this.rightClicks.add(System.currentTimeMillis());
        }

    }

    @EventTarget
    public void onKeyInput(EventKeyInput event) {
        int leftClick = this.mc.gameSettings.keyBindAttack.getKeyCode();
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == leftClick) {
            this.leftClicks.add(System.currentTimeMillis());
        }

        int rightClick = this.mc.gameSettings.keyBindUseItem.getKeyCode();
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == rightClick) {
            this.rightClicks.add(System.currentTimeMillis());
        }

    }
}
