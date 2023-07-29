package top.fpsmaster.core.values.values;

import top.fpsmaster.core.values.Value;
import top.fpsmaster.event.EventManager;
import top.fpsmaster.event.events.impl.client.EventValue;

public class ModeValue extends Value<String> {
    private String[] modes;

    public ModeValue(String name, String current, String... modes) {
        super(name);
        this.value = current;
        this.modes = modes;
    }

    public String getCurrent() {
        return value;
    }

    public void setCurrent(String current) {
        this.value = current;
        if (onValue)
            EventManager.call(new EventValue(this));
    }

    public String[] getModes() {
        return modes;
    }

    public void setModes(String[] modes) {
        this.modes = modes;
    }
}
