package top.fpsmaster.core.vac.check.impl.combat;

import top.fpsmaster.utils.special.vac.Block;
import top.fpsmaster.utils.special.vac.Player;

public class CombatCheck {
    //public boolean draggable;
    private static Player targetPlayer;
    private static Block targetBlock;
    protected int vl = 0;

    public static Player getTargetPlayer() {
        return targetPlayer;
    }

    public static Block getTargetBlock() {
        return targetBlock;
    }

    public static void setTargetPlayer(Player targetPlayer) {
        CombatCheck.targetPlayer = targetPlayer;
    }

    public static void setTargetBlock(Block targetBlock) {
        CombatCheck.targetBlock = targetBlock;
    }
}
