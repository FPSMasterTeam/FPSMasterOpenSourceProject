package top.fpsmaster.core.managers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import top.fpsmaster.core.vac.AntiCheat;
import top.fpsmaster.core.vac.check.Check;
import top.fpsmaster.core.vac.check.impl.combat.AutoClickerCheck;
import top.fpsmaster.core.vac.check.impl.combat.CombatCheck;
import top.fpsmaster.core.vac.check.impl.combat.HitBoxCheck;
import top.fpsmaster.core.vac.check.impl.combat.ReachCheck;
import top.fpsmaster.core.vac.check.impl.misc.ByteCheck;
import top.fpsmaster.core.vac.check.impl.misc.MiscCheck;
import top.fpsmaster.core.vac.check.impl.misc.TimerCheck;
import top.fpsmaster.core.vac.check.impl.movement.FlyCheck;
import top.fpsmaster.core.vac.check.impl.movement.MoveCheck;
import top.fpsmaster.utils.math.TimerUtil;
import top.fpsmaster.utils.special.vac.Block;
import top.fpsmaster.utils.special.vac.Player;

import java.util.Objects;

public final class CheckManager {
    private boolean status = false;
    public static final int UPDATE_WALKING = 0;
    public static final int PACKET_SEND = 1;
    public static final int PACKET_RECEIVE = 2;
    public static final int TICK = 3;
    public static final int LEFT_CLICK = 4;

    private final TimerUtil timer = new TimerUtil();

    public MiscCheck miscCheck;

    private final Check[] checks = new Check[]{
            new ByteCheck(),
            new FlyCheck(),
            new AutoClickerCheck(),
            new ReachCheck(),
            new HitBoxCheck(),
            new TimerCheck()};

    public void init() {
        if (!this.status) {
            this.status = true;
            miscCheck = new MiscCheck();
        }
    }

    public void execute(int type, Object... objects) {
        if (!AntiCheat.active) return;
        switch (type) {
            case PACKET_SEND:
                if (objects[0] instanceof C03PacketPlayer) {
                    C03PacketPlayer packetPlayer = (C03PacketPlayer) objects[0];
                    MoveCheck.updateLastReportedPosition(packetPlayer);
                }
                for (Check check : this.checks) {
                    check.execute(type, objects);
                }
                break;
            case PACKET_RECEIVE:
                if (objects[0] instanceof S39PacketPlayerAbilities) {
                    ((FlyCheck) Objects.requireNonNull(this.getCheckByClass(FlyCheck.class))).lastReceivedCapabilityPacket = (S39PacketPlayerAbilities) objects[0];
                }
                for (Check check : this.checks) {
                    check.execute(type, objects);
                }
                break;
            case TICK:
                if (this.timer.delay(5000L)) {
                    MiscCheck.getValue().entrySet().removeIf(entry -> entry.getKey().startsWith("_"));
                    this.miscCheck.addValue("_EntityPlayerSelfPlayer", MiscCheck.getClassBytes(EntityPlayerSP.class.getName()));
                    this.miscCheck.addValue("_Minecraft", MiscCheck.getClassBytes(Minecraft.class.getName()));
                    this.timer.reset();
                }
                for (Check check : this.checks) {
                    check.execute(type);
                }
                break;
            case LEFT_CLICK:
                if (objects.length == 0) {
                    for (Check check : this.checks) {
                        check.execute(type);
                    }
                    break;
                }
                MovingObjectPosition.MovingObjectType movingObjectType = (MovingObjectPosition.MovingObjectType) objects[0];
                if (movingObjectType == MovingObjectPosition.MovingObjectType.ENTITY) {
                    if (objects[1] instanceof EntityPlayer) {
                        CombatCheck.setTargetPlayer(new Player((Entity) objects[1], (Vec3) objects[2], (Double) objects[3]));
                        CombatCheck.setTargetBlock(null);
                    }
                } else if (movingObjectType == MovingObjectPosition.MovingObjectType.BLOCK) {
                    CombatCheck.setTargetBlock(new Block((BlockPos) objects[1], (EnumFacing) objects[2]));
                    CombatCheck.setTargetPlayer(null);
                } else {
                    CombatCheck.setTargetPlayer(null);
                    CombatCheck.setTargetBlock(null);
                }
                for (Check check : this.checks) {
                    check.execute(type, objects);
                }
                break;
            default:
                break;
        }
    }

    public Check getCheckByClass(Class<? extends Check> checkClass) {
        for (Check check : this.checks) {
            if (check.getClass() != checkClass) continue;
            return check;
        }
        return null;
    }
}
