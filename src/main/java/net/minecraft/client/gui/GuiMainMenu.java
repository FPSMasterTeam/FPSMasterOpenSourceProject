package net.minecraft.client.gui;

import me.superskidder.elementUI.layout.Alignment;
import me.superskidder.elementUI.layout.FlowLayout;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.data.Theme;
import top.fpsmaster.gui.guiScreen.GuiStartup;
import top.fpsmaster.gui.guiScreen.account.GuiAddAccount;
import top.fpsmaster.gui.element.*;
import top.fpsmaster.gui.font.FontLoader;
import top.fpsmaster.gui.font.UFontRenderer;
import top.fpsmaster.utils.render.RenderUtil;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    private static LabelComponent playername;
    private final Theme theme = FPSMaster.INSTANCE.theme;
    private static final FlowLayout menuLayout = new FlowLayout(0, 0, 0, 0, Alignment.Horizontal, 20, false);
    private static final FlowLayout accountLayout = new FlowLayout(0, 0, 20, 0, Alignment.Horizontal, 5, true);

    public GuiMainMenu() {
        if (menuLayout.getElements().size() == 0) {
            playername = new LabelComponent(0, 2, 12, 12, "SuperSkidder", 16, theme.getQuaternary().getRGB(), false);
            accountLayout.useScissor = true;
            menuLayout.addElement(new MenuIconComponent(0, 0, 12, 12, "client/guis/mainmenu/profile24x24.png", "SINGLE PLAYER", () -> mc.displayGuiScreen(new GuiSelectWorld(this))));
            menuLayout.addElement(new MenuIconComponent(0, 0, 12, 12, "client/guis/mainmenu/multi24x24.png", "MULTI PLAYERS", () -> mc.displayGuiScreen(new GuiMultiplayer(this))));
            menuLayout.addElement(new MenuIconComponent(0, 0, 12, 12, "client/guis/mainmenu/settings24x24.png", "OPTIONS", () -> mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings))));
            menuLayout.addElement(new MenuIconComponent(0, 0, 12, 12, "client/guis/mainmenu/accounts24x24.png", "ACCOUNTS", () -> mc.displayGuiScreen(new GuiAddAccount(this))));
            menuLayout.addElement(new RectComponent(0, 1, 1, 12, new Color(137, 137, 137).getRGB(), 0, RectangleType.NORMAL));
            menuLayout.addElement(new MenuIconComponent(0, 0, 12, 12, "client/guis/mainmenu/quit24x24.png", "QUIT", () -> mc.shutdown()));

            accountLayout.addElement(new ImageComponent(0, 0, 12, 12, "client/guis/mainmenu/profile24x24.png",new Color(0,0,0)));
            accountLayout.addElement(playername);
            accountLayout.addElement(new TagComponent(0, 0, 12, 12, theme.getSecondary().getRGB(), -1, 0, "Premium", 14));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);
        playername.setText(mc.session.getUsername());

        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        RenderUtil.drawImage(new ResourceLocation("client/bg.png"), 0, 0, width, height);
        RenderUtil.drawImage(new ResourceLocation("client/guis/mainmenu/logo36x36.png"), 10, height - 28, 18, 18);

        UFontRenderer cFont = FontLoader.getCFont(false, 20);
        String str = FPSMaster.CLIENT_NAME + " " + FPSMaster.VERSION;
        cFont.drawString(str, 34, height - 22, new Color(255, 255, 255).getRGB());
        String str2 = "Developed by FPSMaster Team.";
        cFont.drawString(str2, width - cFont.getStringWidth(str2) - 10, height - 22, new Color(255, 255, 255).getRGB());

        RenderUtil.drawRoundedRect(5, 5, 15 + accountLayout.width, 15 + accountLayout.height, 2, theme.getTertiary().getRGB());
        accountLayout.useScissor = true;
        accountLayout.display(10, 10, mouseX, mouseY);

        RenderUtil.drawImage(new ResourceLocation("client/guis/mainmenu/logo314x58.png"), (width - 314 / 2f) / 2f, height / 2f - 80, 314 / 2f, 58 / 2f);
        RenderUtil.drawImage(new ResourceLocation("client/guis/mainmenu/language24x24.png"), width - 27, 3, 24, 24);
        RenderUtil.drawRoundedRect((width - menuLayout.width) / 2f - 10, (height - menuLayout.height) / 2f - 10, (width + menuLayout.width) / 2f + 10, (height + menuLayout.height) / 2f + 10, 2, theme.getTertiary().getRGB());
        menuLayout.display((width - menuLayout.width) / 2f, (height - menuLayout.height) / 2f, mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        menuLayout.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovered(width - 27, 3, width - 3, 27, mouseX, mouseY)) {
            mc.displayGuiScreen(new GuiStartup(this));
        }
    }
}
