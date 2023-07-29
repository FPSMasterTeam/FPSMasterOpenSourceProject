package top.fpsmaster.modules.settings;

import net.minecraft.client.shader.Framebuffer;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.ColorValue;
import top.fpsmaster.core.values.values.ModeValue;
import top.fpsmaster.core.values.values.NumberValue;
import top.fpsmaster.event.EventManager;
import top.fpsmaster.event.EventTarget;
import top.fpsmaster.event.events.impl.client.EventValue;
import top.fpsmaster.event.events.impl.render.EventRender2D;
import top.fpsmaster.event.events.impl.render.EventShader;
import top.fpsmaster.gui.font.FontLoader;
import top.fpsmaster.utils.math.TimerUtil;
import top.fpsmaster.utils.render.*;

import java.awt.*;

import static top.fpsmaster.utils.render.GaussianBlur.createFrameBuffer;

public class ClientSettings extends Module {
    public static BooleanValue betterInventory = new BooleanValue("BetterInventory", true);
    public static BooleanValue betterButton = new BooleanValue("BetterButton", true);
    public static BooleanValue chatBackground = new BooleanValue("ChatBackground", true);
    public static ColorValue chatBackgroundColor = new ColorValue("ChatBackgroundColor", new Color(0, 0, 0, 125));
    public static BooleanValue chatBar = new BooleanValue("ChatBar", true);
    public static ColorValue chatBarColor = new ColorValue("ChatBarColor", new Color(52, 141, 255));
    public static BooleanValue screenAnimation = new BooleanValue("ClientAnimation", true);
    public static BooleanValue chunkAnimation = new BooleanValue("ChunkAnimation", false);

    public static BooleanValue debug = new BooleanValue("GuiDebug", false);
    public static BooleanValue hideTabName = new BooleanValue("HideTabName", false);
    public static BooleanValue hideChat = new BooleanValue("HideChat", false);
    public static ModeValue mcFont = new ModeValue("ClientFont", "Original", "Original", "Client");
    public static ModeValue blurMode = new ModeValue("BlurMode", "Gaussian", "Gaussian", "Kawase");
    public static BooleanValue fancy = new BooleanValue("Fancy", false);
    //    public static BooleanValue blur = new BooleanValue("Blur", false);
//    public static BooleanValue shadow = new BooleanValue("Shadow", true);
    public static NumberValue<Number> radius = new NumberValue<>("BlurRadius", 10, 1, 50, 1);
    public static NumberValue<Number> iterations = new NumberValue<>("BlurIterations", 4, 0, 15, 1);
    public static NumberValue<Number> shadowRadius = new NumberValue<>("ShadowRadius", 6, 0, 20, 1);
    public static NumberValue<Number> offset = new NumberValue<>("BlurOffset", 3, 1, 15, 1);
    public static NumberValue<Number> shadowOffset = new NumberValue<>("ShadowOffset", 2, 0, 15, 1);
    public static TimerUtil shaderTimer = new TimerUtil();


    public ClientSettings(String name, String desc) {
        super(name, desc, ModuleCategory.Settings);
        mcFont.setOnValue(true);
        addValues(betterInventory, betterButton, chatBackground, chatBar, screenAnimation, chunkAnimation, chatBackgroundColor, chatBarColor, debug, hideTabName, hideChat, mcFont
                , blurMode, fancy, radius, iterations, shadowRadius, offset, shadowOffset);

        this.canBeEnabled = false;
        EventManager.register(this);
    }

    private static Framebuffer bloomFramebuffer = new Framebuffer(1, 1, false);


    public static void blurScreen() {

        if (fancy.getValue()) {
            StencilUtil.initStencilToWrite();
            EventManager.call(new EventShader(EventShader.Type.Blur));
            StencilUtil.readStencilBuffer(1);
            switch (blurMode.getCurrent()) {
                case "Gaussian":
                    GaussianBlur.renderBlur(radius.getValue().floatValue());
                    break;
                case "Kawase":
                    KawaseBlur.renderBlur(iterations.getValue().intValue(), offset.getValue().intValue());
                    break;
            }
            StencilUtil.uninitStencilBuffer();


            bloomFramebuffer = createFrameBuffer(bloomFramebuffer);
            bloomFramebuffer.framebufferClear();
            bloomFramebuffer.bindFramebuffer(true);
            EventManager.call(new EventShader(EventShader.Type.Shadow));
            bloomFramebuffer.unbindFramebuffer();
            BloomUtil.renderBlur(bloomFramebuffer.framebufferTexture, shadowRadius.getValue().intValue(), shadowOffset.getValue().intValue());
        }
    }


    @EventTarget
    public void onRender2D(EventRender2D e){



    }

    @EventTarget
    public void onValue(EventValue e) {
        if (e.getValue().name.equals("MCFont")) {
            switch (((ModeValue) e.getValue()).getCurrent()) {
                case "Original":
                    mc.fontRendererObj = mc.backupFont;
                    break;
                case "Client":
                    mc.fontRendererObj = FontLoader.getCFont(false, 18);
                    break;
            }
        }
    }
}
