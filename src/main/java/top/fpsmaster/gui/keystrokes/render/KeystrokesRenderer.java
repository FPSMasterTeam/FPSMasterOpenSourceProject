package top.fpsmaster.gui.keystrokes.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.keystrokes.KeyStrokes;
import top.fpsmaster.gui.keystrokes.keys.impl.*;

public class KeystrokesRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final Key[] movementKeys = new Key[4];
    private final CPSKey[] cpsKeys = new CPSKey[1];
    private final FPSKey[] fpsKeys = new FPSKey[1];
    private final PingKey[] pingKeys = new PingKey[1];
    private final TimeKey[] timeKeys = new TimeKey[1];
    private final SpaceKey[] spaceKey = new SpaceKey[1];
    private final MouseButton[] mouseButtons = new MouseButton[2];
    private final SpaceKey[] sneakKeys = new SpaceKey[1];

    public KeystrokesRenderer(KeyStrokes mod) {
        this.movementKeys[0] = new Key(mod, this.mc.gameSettings.keyBindForward, 26, 2);
        this.movementKeys[1] = new Key(mod, this.mc.gameSettings.keyBindBack, 26, 26);
        this.movementKeys[2] = new Key(mod, this.mc.gameSettings.keyBindLeft, 2, 26);
        this.movementKeys[3] = new Key(mod, this.mc.gameSettings.keyBindRight, 50, 26);
        this.cpsKeys[0] = new CPSKey(mod, 2, 110);
        this.fpsKeys[0] = new FPSKey(mod, 2, 128);
        this.pingKeys[0] = new PingKey(mod, 2, 146);
        this.timeKeys[0] = new TimeKey(mod, 2, 164);
        this.sneakKeys[0] = new SpaceKey(mod, this.mc.gameSettings.keyBindSneak, 2, 92, "Sneak");
        this.spaceKey[0] = new SpaceKey(mod, this.mc.gameSettings.keyBindJump, 2, 74, "Space");
        this.mouseButtons[0] = new MouseButton(mod, 0, 2, 50);
        this.mouseButtons[1] = new MouseButton(mod, 1, 38, 50);
    }

    public CPSKey[] getCPSKeys() {
        return this.cpsKeys;
    }

    public void renderKeystrokes() {
        ScaledResolution sr = new ScaledResolution(mc);
        int x = (int) (FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes").x * sr.getScaledWidth());
        int y = (int) (FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes").y * sr.getScaledHeight());
        boolean showingMouseButtons = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showMouseButtons.getValue();
        boolean showingSpacebar = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showSpacebar.getValue();
        boolean showingCPS = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showCPS.getValue();
        boolean showingCPSOnButtons = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showCPSOnButtons.getValue();
        boolean showingSneak = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showSneak.getValue();
        boolean showingFPS = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showFPS.getValue();
        boolean showingPing = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showPing.getValue();
        boolean showingWASD = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showWASD.getValue();
        boolean showingTime = ((top.fpsmaster.modules.render.KeyStrokes) FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes")).showTime.getValue();
        if (FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes").scale != 1.0D) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes").scale, FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes").scale, 1.0D);
        }
        if (showingWASD) {
            this.drawMovementKeys(x, y);
        }
        if (showingMouseButtons) {
            this.drawMouseButtons(x, y);
        }
        if (showingCPS && !showingCPSOnButtons) {
            this.drawCPSKeys(x, y);
        }
        if (showingSpacebar) {
            this.drawSpacebar(x, y);
        }
        if (showingSneak) {
            this.drawSneak(x, y);
        }
        if (showingFPS) {
            this.drawFPS(x, y);
        }
        if (showingPing) {
            this.drawPing(x, y);
        }
        if (showingTime) {
            this.drawTime(x, y);
        }
        if (FPSMaster.INSTANCE.moduleManager.modules.get("KeyStrokes").scale != 1.0D) {
            GlStateManager.popMatrix();
        }
    }

    private void drawSneak(int x, int y) {
        for (SpaceKey sneakKey : this.sneakKeys) {
            sneakKey.renderKey(x, y);
        }
    }

    private void drawFPS(int x, int y) {
        for (FPSKey fpsKey : this.fpsKeys) {
            fpsKey.renderKey(x, y);
        }
    }

    private void drawPing(int x, int y) {
        for (PingKey pingKey : this.pingKeys) {
            pingKey.renderKey(x, y);
        }
    }

    private void drawTime(int x, int y) {
        for (TimeKey timeKey : this.timeKeys) {
            timeKey.renderKey(x, y);
        }
    }

    private void drawMovementKeys(int x, int y) {
        for (Key key : this.movementKeys) {
            key.renderKey(x, y);
        }
    }

    private void drawCPSKeys(int x, int y) {
        for (CPSKey key : this.cpsKeys) {
            key.renderKey(x, y);
        }
    }

    private void drawSpacebar(int x, int y) {
        for (SpaceKey key : this.spaceKey) {
            key.renderKey(x, y);
        }
    }

    private void drawMouseButtons(int x, int y) {
        for (MouseButton button : this.mouseButtons) {
            button.renderKey(x, y);
        }
    }
}
