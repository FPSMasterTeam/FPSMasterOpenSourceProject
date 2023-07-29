package top.fpsmaster.core.values;

import top.fpsmaster.event.EventManager;
import top.fpsmaster.event.events.impl.client.EventValue;
import top.fpsmaster.utils.math.AnimationUtils;

public class Value<T> {
    protected T value;
    public String name;
    private boolean hidden;
    public AnimationUtils animationUtils = new AnimationUtils();
    public float animation;
    public boolean onValue;

    public Value(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        if (onValue)
            EventManager.call(new EventValue(this));
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setOnValue(boolean onValue) {
        this.onValue = onValue;
    }
}
