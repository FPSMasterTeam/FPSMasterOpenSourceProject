package top.fpsmaster.utils.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.I18N.I18NUtils;
import top.fpsmaster.gui.font.FontLoader;
import top.fpsmaster.gui.font.UFontRenderer;

import java.awt.*;

public final class GuiInputField extends Gui {
    public float x;
    public float y;
    private final UFontRenderer fontRendererInstance = FontLoader.getCFont(false, 18);
    private int cursorCounter;
    private String text;
    private int maxStringLength;
    public float width;
    public float height;
    private final boolean visible;
    private int selectionEnd;
    private int cursorPosition;
    private int lineScrollOffset;
    private final boolean isEnabled;

    private boolean selected;
    private int tick;

    public GuiInputField(final float x, final float y, final float par5Width, final float par6Height) {
        this.text = "";
        this.maxStringLength = 100;
        this.tick = 0;
        this.visible = true;
        this.isEnabled = true;
        this.selected = false;
        this.x = x;
        this.y = y;
        this.width = par5Width;
        this.height = par6Height;
    }

    private boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
    }

    public void draw(int mouseX, int mouseY) {
        if (this.isVisible()) {
            if (this.isHovered(x - 1, y - 1, x + width + 1, y + height + 1, mouseX, mouseY) || this.selected) {
                RenderUtil.drawRoundedRect(x - 1, y - 1, x + width + 1, y + height + 1, 3, FPSMaster.INSTANCE.theme.getPrimary().getRGB());
            }
            RenderUtil.drawRoundedRect(x, y, x + width, y + height, 2, FPSMaster.INSTANCE.theme.getNearlyWhite().getRGB());
            final int var2 = this.cursorPosition - this.lineScrollOffset;
            int var3 = this.selectionEnd - this.lineScrollOffset;
            final String var4 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), ((int) this.getWidth()));
            final boolean var5 = var2 >= 0 && var2 <= var4.length();
            final boolean var6 = this.selected && this.cursorCounter / 6 % 2 == 0 && var5;
            final float var7 = (this.x + 4);
            final float var8 = (this.y + (this.height - 8) / 2);
            float var9 = var7;
            if (var3 > var4.length()) {
                var3 = var4.length();
            }
            if (var4.length() > 0) {
                String text = var5 ? var4.substring(0, var2) : var4;
                this.fontRendererInstance.drawString(text, var7, this.y + 4, FPSMaster.INSTANCE.theme.getOffline().getRGB());
                var9 += this.fontRendererInstance.getStringWidth(text);
            }
            final boolean var11 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
            float var12 = var9;
            if (!var5) {
                var12 = ((var2 > 0) ? (var7 + this.width) : var7);
            } else if (var11) {
                var12 = var9 - 1;
                --var9;
            }
            if (var4.length() > 0 && var5 && var2 < var4.length()) {
                this.fontRendererInstance.drawString(var4.substring(var2), var9, this.y + 4, FPSMaster.INSTANCE.theme.getOffline().getRGB());
            }
            if (var6) {
                if (var11) {
                    Gui.drawRect(var12, var8 - 1, var12 + 1, var8 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
                } else if (this.tick > 10) {
                    this.fontRendererInstance.drawString(this.text.isEmpty() ? "|" : "_", var12, this.y + 4, FPSMaster.INSTANCE.theme.getOffline().getRGB());
                }
            }
            if (var3 != var2) {
                this.func_146188_c();
            }
            if (!this.selected && this.text.isEmpty()) {
                RenderUtil.drawImage(new ResourceLocation("client/guis/account/edit.png"), x + 4, this.y + 4, 10, 10,new Color(0,0,0));
                this.fontRendererInstance.drawString(I18NUtils.getString("input.here"), x + 18, this.y + 4, FPSMaster.INSTANCE.theme.getGrey().getRGB());
            }
        }
    }

    public void deleteFromCursor(final int p_146175_1_) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                final boolean flag = p_146175_1_ < 0;
                final int i = flag ? (this.cursorPosition + p_146175_1_) : this.cursorPosition;
                final int j = flag ? this.cursorPosition : (this.cursorPosition + p_146175_1_);
                String s = "";
                if (i >= 0) {
                    s = this.text.substring(0, i);
                }
                if (j < this.text.length()) {
                    s = s + this.text.substring(j);
                }
                this.text = s;
                if (flag) {
                    this.moveCursorBy(p_146175_1_);
                }
            }
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void deleteWords(final int p_146177_1_) {
        if (this.text.length() != 0) {
            if (this.selectionEnd != this.cursorPosition) {
                this.writeText("");
            } else {
                this.deleteFromCursor(this.getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
            }
        }
    }

    public void moveCursorBy(final int p_146182_1_) {
        this.setCursorPosition(this.selectionEnd + p_146182_1_);
    }

    public int getNthWordFromPos(final int p_146183_1_) {
        return this.func_146197_a(p_146183_1_, this.func_146198_h(), true);
    }

    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    public int getNthWordFromCursor(final int p_146187_1_) {
        return this.getNthWordFromPos(p_146187_1_);
    }

    private void func_146188_c() {
        GL11.glColor4f(0.0f, 0.0f, 255.0f, 255.0f);
        GL11.glDisable(3553);
        GL11.glEnable(3058);
        GL11.glLogicOp(5387);
        GL11.glDisable(3058);
        GL11.glEnable(3553);
    }

    public void setCursorPosition(final int p_146190_1_) {
        this.cursorPosition = p_146190_1_;
        final int var2 = this.text.length();
        if (this.cursorPosition < 0) {
            this.cursorPosition = 0;
        }
        if (this.cursorPosition > var2) {
            this.cursorPosition = var2;
        }
        this.setSelectionPos(this.cursorPosition);
    }

    public void writeText(final String p_146191_1_) {
        String s = "";
        final String s1 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
        final int i = Math.min(this.cursorPosition, this.selectionEnd);
        final int j = Math.max(this.cursorPosition, this.selectionEnd);
        final int k = this.maxStringLength - this.text.length() - (i - j);
        if (this.text.length() > 0) {
            s = s + this.text.substring(0, i);
        }
        int l;
        if (k < s1.length()) {
            s = s + s1.substring(0, k);
            l = k;
        } else {
            s = s + s1;
            l = s1.length();
        }
        if (this.text.length() > 0 && j < this.text.length()) {
            s = s + this.text.substring(j);
        }
        this.text = s;
        this.moveCursorBy(i - this.selectionEnd + l);
    }

    public void setCursorPositionZero() {
        this.setCursorPosition(0);
    }

    public int func_146197_a(final int p_146197_1_, final int p_146197_2_, final boolean p_146197_3_) {
        int var4 = p_146197_2_;
        final boolean var5 = p_146197_1_ < 0;
        for (int var6 = Math.abs(p_146197_1_), var7 = 0; var7 < var6; ++var7) {
            if (var5) {
                do {
                    --var4;
                } while (!p_146197_3_ || var4 <= 0 || this.text.charAt(var4 - 1) == ' ');
                while (--var4 > 0) {
                    if (this.text.charAt(var4 - 1) == ' ') {
                        break;
                    }
                }
            } else {
                final int var8 = this.text.length();
                var4 = this.text.indexOf(32, var4);
                if (var4 == -1) {
                    var4 = var8;
                } else {
                    while (p_146197_3_ && var4 < var8 && this.text.charAt(var4) == ' ') {
                        ++var4;
                    }
                }
            }
        }
        return var4;
    }

    public int func_146198_h() {
        return this.cursorPosition;
    }

    public void setSelectionPos(int p_146199_1_) {
        final int var2 = this.text.length();
        if (p_146199_1_ > var2) {
            p_146199_1_ = var2;
        }
        if (p_146199_1_ < 0) {
            p_146199_1_ = 0;
        }
        this.selectionEnd = p_146199_1_;
        if (this.fontRendererInstance != null) {
            if (this.lineScrollOffset > var2) {
                this.lineScrollOffset = var2;
            }
            final int var3 = ((int) this.getWidth());
            final String var4 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), var3);
            final int var5 = var4.length() + this.lineScrollOffset;
            if (p_146199_1_ == this.lineScrollOffset) {
                this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, var3, true).length();
            }
            if (p_146199_1_ > var5) {
                this.lineScrollOffset += p_146199_1_ - var5;
            } else if (p_146199_1_ <= this.lineScrollOffset) {
                this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
            }
            if (this.lineScrollOffset < 0) {
                this.lineScrollOffset = 0;
            }
            if (this.lineScrollOffset > var2) {
                this.lineScrollOffset = var2;
            }
        }
    }

    public float getWidth() {
        return this.width - 8;
    }

    public void setCursorPositionEnd() {
        this.setCursorPosition(this.text.length());
    }

    public void setMaxStringLength(final int p_146203_1_) {
        this.maxStringLength = p_146203_1_;
        if (this.text.length() > p_146203_1_) {
            this.text = this.text.substring(0, p_146203_1_);
        }
    }

    public String getSelectedText() {
        final int var1 = Math.min(this.cursorPosition, this.selectionEnd);
        final int var2 = Math.max(this.cursorPosition, this.selectionEnd);
        return this.text.substring(var1, var2);
    }

    public int getMaxStringLength() {
        return this.maxStringLength;
    }

    public String getText() {
        return this.text;
    }

    public void mouseClicked(final int mouseX, final int mouseY, final int p_146192_3_) {
        this.selected = this.isHovered(x - 1, y - 1, x + width + 1, y + height + 1, mouseX, mouseY);
        if (this.selected && p_146192_3_ == 0) {
            int var5 = mouseX - ((int) this.x);
            var5 -= 4;
            final String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), ((int) this.getWidth()));
            this.setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, var5).length() + this.lineScrollOffset);
        }
    }

    public void setSelected(final boolean selected) {
        if (selected && !this.selected) {
            this.cursorCounter = 0;
        }
        this.selected = selected;
    }

    public void setText(final String p_146180_1_) {
        if (p_146180_1_.length() > this.maxStringLength) {
            this.text = p_146180_1_.substring(0, this.maxStringLength);
        } else {
            this.text = p_146180_1_;
        }
        this.setCursorPositionEnd();
    }

    public boolean keyTyped(final char p_146201_1_, final int p_146201_2_) {
        if (!this.selected) {
            return false;
        } else if (GuiScreen.isKeyComboCtrlA(p_146201_2_)) {
            this.setCursorPositionEnd();
            this.setSelectionPos(0);
            return true;
        } else if (GuiScreen.isKeyComboCtrlC(p_146201_2_)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        } else if (GuiScreen.isKeyComboCtrlV(p_146201_2_)) {
            if (this.isEnabled) {
                this.writeText(GuiScreen.getClipboardString());
            }
            return true;
        } else if (GuiScreen.isKeyComboCtrlX(p_146201_2_)) {
            GuiScreen.setClipboardString(this.getSelectedText());
            if (this.isEnabled) {
                this.writeText("");
            }
            return true;
        } else {
            switch (p_146201_2_) {
                case 14:
                    if (GuiScreen.isCtrlKeyDown()) {
                        if (this.isEnabled) {
                            this.deleteWords(-1);
                        }
                    } else if (this.isEnabled) {
                        this.deleteFromCursor(-1);
                    }
                    return true;
                case 199:
                    if (GuiScreen.isShiftKeyDown()) {
                        this.setSelectionPos(0);
                    } else {
                        this.setCursorPositionZero();
                    }
                    return true;
                case 203:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.setSelectionPos(this.getNthWordFromPos(-1));
                        } else {
                            this.setSelectionPos(this.getSelectionEnd() - 1);
                        }
                    } else if (GuiScreen.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor(-1));
                    } else {
                        this.moveCursorBy(-1);
                    }
                    return true;
                case 205:
                    if (GuiScreen.isShiftKeyDown()) {
                        if (GuiScreen.isCtrlKeyDown()) {
                            this.setSelectionPos(this.getNthWordFromPos(1));
                        } else {
                            this.setSelectionPos(this.getSelectionEnd() + 1);
                        }
                    } else if (GuiScreen.isCtrlKeyDown()) {
                        this.setCursorPosition(this.getNthWordFromCursor(1));
                    } else {
                        this.moveCursorBy(1);
                    }
                    return true;
                case 207:
                    if (GuiScreen.isShiftKeyDown()) {
                        this.setSelectionPos(this.text.length());
                    } else {
                        this.setCursorPositionEnd();
                    }
                    return true;
                case 211:
                    if (GuiScreen.isCtrlKeyDown()) {
                        if (this.isEnabled) {
                            this.deleteWords(1);
                        }
                    } else if (this.isEnabled) {
                        this.deleteFromCursor(1);
                    }
                    return true;
                default:
                    if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_)) {
                        if (this.isEnabled) {
                            this.writeText(Character.toString(p_146201_1_));
                        }
                        return true;
                    }
                    return false;
            }
        }
    }

    public void updateCursorCounter() {
        ++this.cursorCounter;
    }

    public void update() {
        if (this.selected) {
            this.tick = this.tick == 19 ? 0 : this.tick + 1;
        } else {
            this.tick = 0;
        }
    }
}
