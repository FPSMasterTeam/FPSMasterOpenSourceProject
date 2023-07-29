package top.fpsmaster.gui.classicComponents.base.drawable;

public class Drawable {
    float x;
    float y;
    public boolean hovered;
    public int id;

    public Drawable(int id, float x, float y) {
        this.x = x;
        this.y = y;
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


    public void draw(int mouseX, int mouseY) {
    }


    public void update() {

    }

    public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }


}
