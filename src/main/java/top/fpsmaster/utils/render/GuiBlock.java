package top.fpsmaster.utils.render;

import net.minecraft.client.gui.FontRenderer;

import java.util.Iterator;
import java.util.List;

public class GuiBlock {
    private int left;
    private int right;
    private int top;
    private int bottom;
    private boolean expandRight = true;
    private boolean printRight = false;

    public GuiBlock(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public GuiBlock multiply(double scale) {
        return new GuiBlock((int) ((double) this.left * scale), (int) ((double) this.right * scale), (int) ((double) this.top * scale), (int) ((double) this.bottom * scale));
    }

    public String toString() {
        return "GuiBlock{left=" + this.left + ", right=" + this.right + ", top=" + this.top + ", bottom=" + this.bottom + '}';
    }

    public int getWidth() {
        return this.right - this.left;
    }

    public int getHeight() {
        return this.bottom - this.top;
    }

    public void ensureWidth(int width, boolean scaleRight) {
        if (this.getWidth() < width) {
            if (scaleRight) {
                this.right = this.left + width;
            } else {
                this.left = this.right - width;
            }
        }

    }

    public void ensureHeight(int height, boolean scaleBottom) {
        if (this.getHeight() < height) {
            if (scaleBottom) {
                this.bottom = this.top + height;
            } else {
                this.top = this.bottom - height;
            }
        }

    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return this.right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public boolean isMouseOver(int x, int y) {
        return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
    }

    public int getBottom() {
        return this.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public boolean drawString(List<String> strings, FontRenderer fontRenderer, boolean shadow, boolean center, int xOffset, int yOffset, boolean scaleToFitX, boolean scaleToFixY, int color, boolean sideLeft) {
        boolean suc = true;

        String string;
        for (Iterator<String> var12 = strings.iterator(); var12.hasNext(); suc = suc && this.drawString(string, fontRenderer, shadow, center, xOffset, yOffset, scaleToFitX, scaleToFixY, color, sideLeft)) {
            string = var12.next();
        }

        return suc;
    }

    public void translate(int x, int y) {
        this.left += x;
        this.right += x;
        this.top += y;
        this.bottom += y;
    }

    public void scalePosition(float amount) {
        this.left = (int) ((float) this.left * amount);
        this.right = (int) ((float) this.right * amount);
        this.top = (int) ((float) this.top * amount);
        this.bottom = (int) ((float) this.bottom * amount);
    }

    public boolean drawString(String string, FontRenderer fontRenderer, boolean shadow, boolean center, int xOffset, int yOffset, boolean scaleToFitX, boolean scaleToFixY, int color, boolean sideLeft) {
        int stringWidth = fontRenderer.getStringWidth(string);
        int x;
        if (sideLeft) {
            x = this.left + xOffset;
        } else {
            x = this.right - stringWidth - xOffset;
        }

        int y = this.top + yOffset;
        if (center) {
            x -= stringWidth / 2;
        }

        if (sideLeft) {
            if (x + stringWidth > this.right) {
                if (!scaleToFitX) {
                    return false;
                }

                if (this.expandRight) {
                    this.right = x + stringWidth + xOffset;
                } else {
                    this.left = this.right - stringWidth - xOffset;
                    x = this.left;
                }
            }
        } else if (this.right - stringWidth < this.left) {
            if (!scaleToFitX) {
                return false;
            }

            if (this.expandRight) {
                this.right = x + stringWidth + xOffset;
                x = this.right;
            } else {
                this.left = this.right - stringWidth - xOffset;
            }
        }

        if (y + 10 > this.bottom) {
            if (!scaleToFixY) {
                return false;
            }

            this.bottom = y + 10;
        }

        if (y < this.top) {
            if (!scaleToFixY) {
                return false;
            }

            this.top = y;
        }

        fontRenderer.drawString(string, (float) x, (float) y, color, shadow);
        return true;
    }

    public boolean isExpandRight() {
        return this.expandRight;
    }

    public void setExpandRight(boolean expandRight) {
        this.expandRight = expandRight;
    }

    public boolean isPrintRight() {
        return this.printRight;
    }

    public void setPrintRight(boolean printRight) {
        this.printRight = printRight;
    }
}
