package top.fpsmaster.event.events.impl.client;

import net.minecraft.network.Packet;
import top.fpsmaster.event.events.Event;

public class EventPacketReceive implements Event {
    public boolean cancel;
    private Packet packet;

    public EventPacketReceive(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
