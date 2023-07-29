package top.fpsmaster.gui.guiScreen.account;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.gui.font.FontLoader;
import top.fpsmaster.gui.font.UFontRenderer;
import top.fpsmaster.utils.render.RenderUtil;

import java.io.IOException;

public class GuiAddAccount extends GuiScreen {
    private final GuiScreen previousScreen;
    private final UFontRenderer cFont = FontLoader.getCFont(true, 24);

    public GuiAddAccount(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        RenderUtil.drawImage(new ResourceLocation("client/bg.png"), 0, 0, width, height);
        if (isHovered((float) width / 2 - 101, (float) height / 2 - 47, (float) width / 2 + 101, (float) height / 2 - 8, mouseX, mouseY)) {
            RenderUtil.drawRoundedRect((float) width / 2 - 101, (float) height / 2 - 47, (float) width / 2 + 101, (float) height / 2 - 8, 4, FPSMaster.INSTANCE.theme.getPrimary().getRGB());
        } else if (isHovered((float) width / 2 - 101, (float) height / 2 + 5, (float) width / 2 + 101, (float) height / 2 + 47, mouseX, mouseY)) {
            RenderUtil.drawRoundedRect((float) width / 2 - 101, (float) height / 2 + 5, (float) width / 2 + 101, (float) height / 2 + 47, 7, FPSMaster.INSTANCE.theme.getPrimary().getRGB());
        }
        RenderUtil.drawImage(new ResourceLocation("client/guis/account/ms_background.png"), (float) width / 2 - 110, (float) height / 2 - 54, 220, 56);
        RenderUtil.drawImage(new ResourceLocation("client/guis/account/microsoft.png"), (float) width / 2 - 92, (float) height / 2 - 36, 16, 16);
        cFont.drawString("Microsoft Login", (float) width / 2 - 70, (float) height / 2 - 30, FPSMaster.INSTANCE.theme.getTertiary().getRGB());
        RenderUtil.drawImage(new ResourceLocation("client/guis/account/link.png"), (float) width / 2 + 78, (float) height / 2 - 34, 12, 12);
        RenderUtil.drawImage(new ResourceLocation("client/guis/account/ol_background.png"), (float) width / 2 - 100, (float) height / 2 + 6, 200, 40);
        RenderUtil.drawImage(new ResourceLocation("client/guis/account/offline.png"), (float) width / 2 - 92, (float) height / 2 + 18, 16, 16);
        cFont.drawString("Offline Login", (float) width / 2 - 70, (float) height / 2 + 24, FPSMaster.INSTANCE.theme.getTertiary().getRGB());
        RenderUtil.drawImage(new ResourceLocation("client/guis/account/go.png"), (float) width / 2 + 78, (float) height / 2 + 20, 14, 14);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovered((float) width / 2 - 101, (float) height / 2 - 47, (float) width / 2 + 101, (float) height / 2 - 8, mouseX, mouseY)) {
            GuiMicrosoftLogin.loggedIn = true;
            mc.displayGuiScreen(new GuiMicrosoftLogin(this.previousScreen));
        } else if (isHovered((float) width / 2 - 101, (float) height / 2 + 5, (float) width / 2 + 101, (float) height / 2 + 47, mouseX, mouseY)) {
            mc.displayGuiScreen(new GuiOfflineLogin(this.previousScreen));
        }
    }
}
