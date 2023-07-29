package top.fpsmaster.event.events.impl.client;

import net.minecraft.network.Packet;
import top.fpsmaster.event.events.Event;

public class EventPacketSend implements Event {
    private Packet packet;
    public boolean cancel;

    public EventPacketSend(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
