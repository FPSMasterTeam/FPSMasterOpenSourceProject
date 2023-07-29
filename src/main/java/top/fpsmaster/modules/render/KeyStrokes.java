package top.fpsmaster.modules.render;

import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.Module;
import top.fpsmaster.core.ModuleCategory;
import top.fpsmaster.core.values.values.BooleanValue;
import top.fpsmaster.core.values.values.ColorValue;
import top.fpsmaster.core.values.values.NumberValue;

import java.awt.*;

public class KeyStrokes extends Module {
    public BooleanValue showWASD = new BooleanValue("ShowWASD", false);
    public BooleanValue arrowKeys = new BooleanValue("ArrowKeys", false);
    public BooleanValue showMouseButtons = new BooleanValue("ShowMouseButtons", false);
    public BooleanValue showCPS = new BooleanValue("ShowCPS", false);
    public BooleanValue showCPSOnButtons = new BooleanValue("ShowCPSOnButtons", false);
    public BooleanValue showSpacebar = new BooleanValue("ShowSpacebar", false);
    public BooleanValue showSneak = new BooleanValue("ShowSneak", false);
    public BooleanValue showFPS = new BooleanValue("ShowFPS", false);
    public BooleanValue showPing = new BooleanValue("ShowPing", false);
    public BooleanValue showTime = new BooleanValue("ShowTime", false);
    public BooleanValue chroma = new BooleanValue("Chroma", true);
    public BooleanValue keyBackground = new BooleanValue("KeyBackground", true);
    public NumberValue<Number> fadeTime = new NumberValue<>("FadeTime", 1, 0.1, 10, 0.1);
    public ColorValue color = new ColorValue("Color", new Color(255, 255, 255));
    public ColorValue pressedColor = new ColorValue("PressedColor", new Color(0, 0, 0));
    public ColorValue backgroundColor = new ColorValue("BackgroundColor", new Color(0, 0, 0, 80));
    public ColorValue backgroundPressedColor = new ColorValue("BackgroundPressedColor", new Color(255, 255, 255, 100));

    public KeyStrokes(String name, String desc) {
        super(name, desc, ModuleCategory.Renders);
        this.addValues(showWASD, arrowKeys, showMouseButtons, showCPS, showCPSOnButtons, showSpacebar, showSneak, showFPS, showPing, showTime, chroma, keyBackground, fadeTime, color, pressedColor, backgroundColor, backgroundPressedColor);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onGui() {
        super.onGui();
        width = 74;
        height = 50;

        if (this.showCPS.getValue() || this.showSneak.getValue() || this.showFPS.getValue()) {
            height += 24;
        }

        if (this.showMouseButtons.getValue()) {
            height += 24;
        }

        if (this.showWASD.getValue()) {
            height += 48;
        }

        if (!this.showFPS.getValue()) {
            height -= 18;
        }

        if (!this.showSpacebar.getValue()) {
            height -= 18;
        }

        if (!this.showCPS.getValue()) {
            height -= 18;
        }

        if (this.showCPSOnButtons.getValue()) {
            height -= 18;
        }

        if (!this.showPing.getValue()) {
            height -= 18;
        }

        if (!this.showTime.getValue()) {
            height -= 18;
        }
        FPSMaster.INSTANCE.guiCustom.keyStrokes.getRenderer().renderKeystrokes();
    }
}