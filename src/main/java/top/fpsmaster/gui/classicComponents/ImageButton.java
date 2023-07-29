package top.fpsmaster.gui.classicComponents;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class ImageButton extends Gui {
    public ResourceLocation img;
    float x, y, width, height;
    boolean isFocused;
    Runnable event;

    public ImageButton(ResourceLocation img, float x, float y, float width, float height, Runnable event) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.event = event;
    }

    public void drawButton() {
        RenderUtil.drawImage(img, x, y, width, height, isFocused ? new Color(255, 255, 255) : new Color(255, 255, 255, 200));
    }

    public void mouseReleased(int mouseX, int mouseY) {
        isFocused = isHovered(x, y, x + width, y + height, mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0) {
            if (isFocused) {
                event.run();
            }
        }
    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
