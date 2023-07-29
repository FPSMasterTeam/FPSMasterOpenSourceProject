package top.fpsmaster.event.events.impl.player;

import top.fpsmaster.event.events.Event;

public class EventMotion implements Event {
    public EventType type;

    public EventMotion(EventType e) {
        this.type = e;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

}
