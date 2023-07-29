package top.fpsmaster.core.managers;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.script.FpsMasterScript;
import top.fpsmaster.event.EventManager;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.misc.EventKey;
import top.fpsmaster.modules.boost.Sprint;
import top.fpsmaster.modules.misc.AutoGG;
import top.fpsmaster.modules.misc.CheaterDetector;
import top.fpsmaster.modules.misc.MemoryManager;
import top.fpsmaster.modules.render.*;
import top.fpsmaster.modules.settings.ClientSettings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager extends Manager {
    public Map<String, Module> modules = new HashMap<>();
    public Map<String, FpsMasterScript> scriptModules = new HashMap<>();

    public void init() {
        List<Module> modules = Arrays.asList(
//                new Debugger("Debugger", "Debugger for developer."),
                new Sprint("Sprint", "Auto toggle sprint"),
                new ClickGui("ClickGui", "Change settings."),
                new OldAnimation("OldAnimation", "OldAnimation."),
                new MoreParticles("MoreParticles", "Display more particles."),
                new CustomFov("CustomFov", "Change fov."),
                new PotionDisplay("PotionDisplay", "Display potions."),
                new MemoryManager("MemoryManager", "Manage memory."),
                new AutoGG("AutoGG", "Automatic send \"GG\" when game ends."),
                new KeyStrokes("KeyStrokes", "Display key buttons."),
                new MinimizedBobbing("MinimizedBobbing", "Cancel the bobbing of the whole-viewing."),
                new FullBright("FullBright", "Full your bright."),
                new NameTag("NameTag", "Show nametag of yourself."),
                new Scoreboard("Scoreboard", "Scoreboard."),
                new TimeChanger("TimeChanger", "TimeChanger."),
                new DragonWings("DragonWings", "DragonWings"),
                new BlockOverlay("BlockOverlay", "BlockOverlay"),
                new NotificationModule("Notification", "Notification"),
                new ItemPhysics("ItemPhysics", "Drop items like in reality."),
                new Crosshair("Crosshair", "Custom Crosshair"),
                new TNTTimer("TNTTimer", "Show TNT Timer"),
                new MotionBlur("MotionBlur", "blur when screen updated"),
                new Coordinates("Coordinates", "Show coordinates"),
                new SnapLook("SnapLook", "SnapLook"),
                new ArmorStatus("ArmorStatus", "ArmorStatus"),
                new ClientSettings("ClientSettings", "ClientSettings"),
                new CheaterDetector("CheaterDetector", "Detect the cheaters on the server.")
        );

        for (ModuleCategory value : ModuleCategory.values()) {
            for (Module module : modules) {
                if (module.type.equals(value)) {
                    this.modules.put(module.name, module);
                }
            }
        }

        EventManager.register(this);
    }

    @EventTarget
    public void onKey(EventKey e) {
        for (Map.Entry<String, Module> m : modules.entrySet()) {
            if (m.getValue().key == e.key) {
                m.getValue().toggle();
            }
        }
        for (Map.Entry<String, FpsMasterScript> m : scriptModules.entrySet()) {
            if (m.getValue().getScriptModule().key == e.key) {
                m.getValue().getScriptModule().toggle();
            }
        }
    }
}

