package top.fpsmaster.gui.classicComponents;

import net.minecraft.client.gui.Gui;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class RoundButton extends Gui {
    public boolean enabled;
    float x, y, width, height;
    boolean isFocused;
    Runnable event;
    Color c1, c2, Tc1, Tc2;
    public String string;
    boolean multiThreads;

    public RoundButton(String text, float x, float y, float width, float height, Color c1, Color c2, Color Tc1, Color Tc2, Runnable event, boolean multiThreads) {
        this.string = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.event = event;
        this.c1 = c1;
        this.c2 = c2;
        this.Tc1 = Tc1;
        this.Tc2 = Tc2;
        this.enabled = true;
        this.multiThreads = multiThreads;
    }

    public void drawButton() {
        RenderUtil.drawShadow(x, y, x + width, y + height, -1);
        RenderUtil.drawRoundedRect2(x, y, x + width, y + height, isFocused ? c1.getRGB() : c2.getRGB());
        FPSMaster.INSTANCE.fontLoader.client18.drawCenteredString(string, x + width / 2, y + height / 2 - FPSMaster.INSTANCE.fontLoader.arial18.getHeight() / 2.0f, isFocused ? Tc1.getRGB() : Tc2.getRGB());
    }

    public void mouseReleased(int mouseX, int mouseY) {
        isFocused = isHovered(x, y, x + width, y + height, mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0 && this.enabled) {
            if (isFocused) {
                if (this.multiThreads) {
                    new Thread(event).start();
                    return;
                }
                event.run();
            }
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
