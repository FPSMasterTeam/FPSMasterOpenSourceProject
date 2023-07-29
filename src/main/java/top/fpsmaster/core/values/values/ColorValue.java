package top.fpsmaster.core.values.values;

import top.fpsmaster.core.values.Value;

import java.awt.*;

public class ColorValue extends Value<Color> {

    public ColorValue(String name, Color color) {
        super(name);
        this.name = name;
        this.value = color;
    }

    public int getColor() {
        return value.getRGB();
    }

    public int getAlpha() {
        return value.getAlpha();
    }

}
