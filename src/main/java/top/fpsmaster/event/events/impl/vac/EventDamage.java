package top.fpsmaster.event.events.impl.vac;

import net.minecraft.util.DamageSource;
import top.fpsmaster.event.events.Event;

public class EventDamage implements Event {
    private final DamageSource damageSource;

    public EventDamage(DamageSource damageSource) {
        this.damageSource = damageSource;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }
}
