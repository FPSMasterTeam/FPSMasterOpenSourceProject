package top.fpsmaster.core.values.values;

import top.fpsmaster.core.values.Value;

public class BooleanValue extends Value<Boolean> {
    public BooleanValue(String name, boolean value, boolean... objects) {
        super(name);
        this.value = value;
        this.setHidden(objects.length != 0 && objects[0]);
    }
}
