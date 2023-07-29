package top.fpsmaster.gui.classicComponents.base.clickable;

import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class ClickableRoundedRect extends Clickable {
    float round;
    Color color;

    public ClickableRoundedRect(float x, float y, float x1, float y1, float round, Color color, Runnable event) {
        super(x, y, event);
        this.x1 = x1;
        this.y1 = y1;
        this.color = color;
        this.round = round;

    }

    @Override
    public void draw2(float mouseX, float mouseY) {
        super.draw2(mouseX, mouseY);
        RenderUtil.drawRoundedRect(x, y, x1, y1, round, color.getRGB());
    }
}
