package top.fpsmaster.core.vac.check.impl.movement;

import net.minecraft.network.play.client.C03PacketPlayer;

public class MoveCheck {
    public static double lastReportedPosX;
    public static double lastReportedPosY;
    public static double lastReportedPosZ;
    protected int vl = 0;

    public static void updateLastReportedPosition(C03PacketPlayer packetPlayer) {
        lastReportedPosX = packetPlayer.getPositionX();
        lastReportedPosY = packetPlayer.getPositionY();
        lastReportedPosZ = packetPlayer.getPositionZ();
    }
}
