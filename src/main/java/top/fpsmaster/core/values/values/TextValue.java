package top.fpsmaster.core.values.values;

import top.fpsmaster.core.values.Value;

public class TextValue extends Value<String> {
    public TextValue(String name, String value, boolean... objects) {
        super(name);
        this.value = value;
        this.setHidden(objects.length != 0 && objects[0]);
    }
}
