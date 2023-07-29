package top.fpsmaster.event.events.impl.client;

import top.fpsmaster.core.values.Value;
import top.fpsmaster.event.events.Event;

public class EventValue implements Event {
    Value value;

    public EventValue(Value value) {
        this.value = value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
