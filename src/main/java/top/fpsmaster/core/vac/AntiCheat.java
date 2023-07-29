package top.fpsmaster.core.vac;

import net.minecraft.network.Packet;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.managers.CheckManager;

public class AntiCheat {
    public static boolean active = false;

    public static void handlePacket(Packet packetIn) {
        if (!active) return;
        FPSMaster.INSTANCE.checkManager.execute(CheckManager.PACKET_SEND, packetIn);
    }
}
