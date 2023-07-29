package top.fpsmaster.modules.render;

import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.NumberValue;

public class MoreParticles extends Module {

    public NumberValue<Number> critParticles = new NumberValue<>("CritParticles", 2, 0, 10, 1);
    public NumberValue<Number> sharpParticles = new NumberValue<>("SharpnessParticles", 1, 0, 10, 1);
    public NumberValue<Number> lavaParticles = new NumberValue<>("BloodParticles", 0, 0, 10, 1);
    public BooleanValue sound = new BooleanValue("HitSound", false);

    public MoreParticles(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        addValues(critParticles, sharpParticles, lavaParticles, sound);
    }

}
