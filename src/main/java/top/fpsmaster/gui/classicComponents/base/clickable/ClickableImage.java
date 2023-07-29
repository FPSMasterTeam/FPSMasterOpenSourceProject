package top.fpsmaster.gui.classicComponents.base.clickable;

import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class ClickableImage extends Clickable {
    public ClickableImage(ResourceLocation img, float x, float y, float width, float height, Color color, Runnable event) {
        super(x, y, event);
    }
}
