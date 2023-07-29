package top.fpsmaster.gui.classicComponents.base.drawable;

import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;

public class DrawableRect extends Drawable {
    float width, height;
    Color cor;

    public DrawableRect(int id, float x, float y, float width, float height, Color color) {
        super(id, x, y);
        this.cor = color;
        this.height = height;
        this.width = width;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        super.draw(mouseX, mouseY);
        RenderUtil.drawRect(x, y, x + width, y + height, cor);
        hovered = isHovered(x, y, x + width, y + height, mouseX, mouseY);
    }


}
