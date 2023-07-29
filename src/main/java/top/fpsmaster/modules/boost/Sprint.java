package top.fpsmaster.modules.boost;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.TextValue;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.player.EventMotion;
import top.fpsmaster.event.events.impl.player.EventType;
import top.fpsmaster.event.events.impl.render.EventShader;
import top.fpsmaster.utils.render.GaussianBlur;
import top.fpsmaster.utils.render.RoundedUtil;

import java.awt.*;

public class Sprint extends Module {
    public static TextValue msg = new TextValue("Message", "[Sprint Enabled]", true);

    public Sprint(String name, String desc) {
        super(name, desc, ModuleCategory.Boost);
        this.addValues(msg);
        key = Keyboard.KEY_I;
    }

    @Override
    public void onGui() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(msg.getValue());
        height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(msg.getValue(), x * sr.getScaledWidth(), y * sr.getScaledHeight(), new Color(200, 200, 200).getRGB());
    }


    @EventTarget
    public void onMotion(EventMotion e) {
        if (e.type.equals(EventType.PRE)) {
            mc.thePlayer.setSprinting(!mc.thePlayer.isDead && mc.thePlayer.getFoodStats().getFoodLevel() > 6 && mc.thePlayer.moveForward > 0.0f && !mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isSneaking() && !mc.thePlayer.isBlocking() && !mc.thePlayer.isUsingItem());
        }
    }
}
