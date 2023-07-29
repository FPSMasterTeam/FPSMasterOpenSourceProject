package top.fpsmaster.event.events.impl.render;

import top.fpsmaster.event.events.Event;

public class EventShader implements Event {

    public enum Type{
        Shadow,
        Blur
    }
    Type type;

    public EventShader(Type type) {
        this.type = type;
    }
}
