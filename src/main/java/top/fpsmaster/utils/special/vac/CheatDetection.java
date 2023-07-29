package top.fpsmaster.utils.special.vac;

import java.util.HashMap;
import java.util.UUID;

public class CheatDetection {
    public final UUID uuid;
    public int reach, sprint, noSlow;
    public int reachPercentage, sprintPercentage, noSlowPercentage;
    public boolean hacks = false;

    public static HashMap<UUID, CheatDetection> cheaters = new HashMap<>();

    public CheatDetection(UUID uuid) {
        this.uuid = uuid;
    }

    public static CheatDetection get(UUID uniqueID) {
        if (cheaters.containsKey(uniqueID)) {
            return cheaters.get(uniqueID);
        }
        CheatDetection detection = new CheatDetection(uniqueID);
        cheaters.put(uniqueID, detection);
        return detection;
    }
}
