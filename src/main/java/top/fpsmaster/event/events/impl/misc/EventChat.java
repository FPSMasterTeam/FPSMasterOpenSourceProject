package top.fpsmaster.event.events.impl.misc;

import top.fpsmaster.event.events.Cancellable;
import top.fpsmaster.event.events.Event;

/**
 * @description: CHATTTTTTTTTTTTTT
 * @author: QianXia
 * @create: 2021/08/19 00:54
 **/
public class EventChat implements Event, Cancellable {
    private String chatMessage;
    private boolean cancelled;
    private Type type;

    public EventChat(String chatMessage, Type type) {
        this.chatMessage = chatMessage;
        this.type = type;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

    public enum Type {
        RECEIVE, SEND
    }
}
