package top.fpsmaster.gui.classicComponents.base.clickable;

import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class ClickableRect extends Clickable {
    float x1, y1;
    Color color;

    public ClickableRect(float x, float y, float x1, float y1, Color color, Runnable event) {
        super(x, y, event);
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
    }

    @Override
    public void draw2(float mouseX, float mouseY) {
        super.draw2(mouseX, mouseY);
        RenderUtil.drawRect(x, y, x1, y1, color);
    }
}
