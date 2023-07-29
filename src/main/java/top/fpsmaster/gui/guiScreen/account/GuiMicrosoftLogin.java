package top.fpsmaster.gui.guiScreen.account;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.api.MicrosoftLogin;
import top.fpsmaster.core.I18N.I18NUtils;
import top.fpsmaster.gui.font.FontLoader;
import top.fpsmaster.gui.font.UFontRenderer;
import top.fpsmaster.utils.render.RenderUtil;

import java.io.IOException;

public class GuiMicrosoftLogin extends GuiScreen {
    public static boolean loggedIn;
    private final GuiScreen previousScreen;
    private int tick;
    private final UFontRenderer cFont = FontLoader.getCFont(true, 24);
    public static String state;

    public GuiMicrosoftLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    public void initGui() {
        this.tick = 1;
        if (loggedIn) {
            loggedIn = false;
            state = I18NUtils.getString("ms.waiting");
            MicrosoftLogin.login();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        RenderUtil.drawImage(new ResourceLocation("client/bg.png"), 0, 0, width, height);
        RenderUtil.drawImage(new ResourceLocation("client/animation/loader" + MathHelper.floor_double(this.tick / 3.0) + ".png"), (float) (width - this.cFont.getStringWidth(state)) / 2 - 12, (float) height / 2 - 7, 10, 10);
        this.cFont.drawCenteredString(state, (float) width / 2, (float) height / 2 - 5, FPSMaster.INSTANCE.theme.getTertiary().getRGB());
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            mc.displayGuiScreen(this.previousScreen);
        }
    }

    @Override
    public void updateScreen() {
        this.tick = this.tick + 1 == 24 ? 0 : this.tick + 1;
        super.updateScreen();
        if (loggedIn) {
            mc.displayGuiScreen(this.previousScreen);
        }
    }
}
