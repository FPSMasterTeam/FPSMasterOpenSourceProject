package top.fpsmaster.gui.classicComponents.base.clickable;

import top.fpsmaster.utils.render.RenderUtil;

public class ClickableCircle extends Clickable {
    float radius;
    int cor;

    public ClickableCircle(float x, float y, float radius, int color, Runnable event) {
        super(x, y, event);
        this.radius = radius;
        this.cor = color;
    }

    @Override
    public void draw2(float mouseX, float mouseY) {
        super.draw2(mouseX, mouseY);
        RenderUtil.circle(x, y, radius, cor);
    }
}
