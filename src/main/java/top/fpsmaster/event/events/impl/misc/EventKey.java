package top.fpsmaster.event.events.impl.misc;

import top.fpsmaster.event.events.Event;

public class EventKey implements Event {
    public int key;

    public EventKey(int key) {
        this.key = key;
    }

}
