package top.fpsmaster.gui.element.data;

import me.superskidder.elementUI.impl.Component;
import org.lwjgl.input.Mouse;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.I18N.I18NUtils;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.data.Theme;
import top.fpsmaster.gui.font.FontLoader;
import top.fpsmaster.gui.font.UFontRenderer;
import top.fpsmaster.utils.math.AnimationUtils;
import top.fpsmaster.utils.render.RenderUtil;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberComponent extends Component {
    private final UFontRenderer cFont;
    private String name;
    private NumberValue value;
    private boolean drag;

    private float numX;
    private float numWidth;
    private float animH;
    AnimationUtils a1 = new AnimationUtils();

    public NumberComponent(float x, float y, float width, float height, String name, NumberValue value) {
        super(x, y, width, height);
        this.name = name;
        this.value = value;
        cFont = FontLoader.getCFont(false, 18);
        numWidth = width;
    }

    @Override
    public void render(float x, float y) {
        super.render(x, y);

        RenderUtil.drawRect(
                x - 2,
                y,
                x + width + 2,
                y + height,
                new Color(220, 220, 220, (int) (animH * 100))
        );

        cFont.drawString(I18NUtils.getString(name), x + this.x, y + 2, Theme.Companion.setBrightness(FPSMaster.INSTANCE.theme.getTertiary(), 0.7f).getRGB());
        int stringWidth = cFont.getStringWidth(I18NUtils.getString(name));
        numX = x + stringWidth + 5;
        RenderUtil.drawRect(numX, y + height / 2 - 1, numX + numWidth, y + height / 2 + 1, Theme.Companion.setBrightness(FPSMaster.INSTANCE.theme.getTertiary(), 0.8f));
        float x1 = numX + ((((Number) value.getValue()).floatValue() - value.getMin().floatValue()) / (value.getMax().floatValue() - value.getMin().floatValue()) * numWidth);
        RenderUtil.drawRect(numX, y + height / 2 - 1, x1, y + height / 2 + 1, FPSMaster.INSTANCE.theme.getPrimary());
//        RenderUtil.drawRect(x1-animC, y + height / 2 - 1 + 5, x1, y + 2 + height / 2 - 1 + 5, theme.clickgui_number);

        // format value to 2 decimal places
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(2);
        String format = nf.format(((Number) value.getValue()).floatValue());
        cFont.drawString(format, x + this.x + numWidth + stringWidth + 10, y + 2, Theme.Companion.setBrightness(FPSMaster.INSTANCE.theme.getTertiary(), 0.5f).getRGB());
    }

    @Override
    public void update(float x, float y, float mouseX, float mouseY) {
        super.update(x, y, mouseX, mouseY);
        if (getHovered()) {

            animH = a1.animate(1.2f, animH, 0.2f);
        } else {
            animH = a1.animate(0, animH, 0.2f);
        }
        int stringWidth = cFont.getStringWidth(name);

        // format value to 2 decimal places
        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
        nf.setMaximumFractionDigits(2);
        String format = nf.format(((Number) value.getValue()).floatValue());
        this.width = stringWidth + 14 + numWidth + cFont.getStringWidth(format);
    }

    @Override
    public void mouseClicked(float x, float y, int btn) {
        super.mouseClicked(x, y, btn);
        if (btn == 0) {
            drag = true;
        }
    }

    @Override
    public void mouseUpdate(float x, float y, float mouseX, float mouseY) {
        super.mouseUpdate(x, y, mouseX, mouseY);
        if (drag && Mouse.isButtonDown(0)) {
            float v = mouseX - numX;
            if (v < 0) {
                v = 0;
            }
            if (v > numWidth) {
                v = numWidth;
            }
            float percent = v / numWidth;
            float value = percent * (this.value.getMax().floatValue() - this.value.getMin().floatValue()) + this.value.getMin().floatValue();
            if (value >= ((Number) this.value.getValue()).floatValue() + this.value.getInc().floatValue())
                this.value.setValue(((Number) this.value.getValue()).floatValue() + this.value.getInc().floatValue());
            if (value <= ((Number) this.value.getValue()).floatValue() - this.value.getInc().floatValue())
                this.value.setValue(((Number) this.value.getValue()).floatValue() - this.value.getInc().floatValue());
            if (value <= this.value.getMin().floatValue())
                this.value.setValue(this.value.getMin().floatValue());
            if (value >= this.value.getMax().floatValue())
                this.value.setValue(this.value.getMax());
        } else {
            drag = false;
        }
    }
}
