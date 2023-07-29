package top.fpsmaster.event.events.impl.client;

import top.fpsmaster.event.events.Event;

public class EventMouseInput implements Event {
    private final int type;

    public EventMouseInput(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
