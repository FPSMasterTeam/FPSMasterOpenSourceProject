package top.fpsmaster.gui.classicComponents.base.clickable;

import org.lwjgl.input.Mouse;

public class Clickable {
    float x;
    float y;
    Runnable event;
    float x1, y1;

    public Clickable(float x, float y, Runnable event) {
        this.x = x;
        this.y = y;
        this.event = event;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setEvent(Runnable event) {
        this.event = event;
    }

    public Runnable getEvent() {
        return event;
    }

    public void draw(float mouseX, float mouseY) {
        if (isHovered(x, y, x1, y1, ((int) mouseX), ((int) mouseY)) && Mouse.isButtonDown(0)) {
            event.run();
        }
        draw2(mouseX, mouseY);
    }

    public void draw2(float mouseX, float mouseY) {

    }

    public void onClick(float mouseX, float mouseY, int button) {

    }

    public void update() {

    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }
}
