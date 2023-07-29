package top.fpsmaster.gui.guiScreen.account;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import top.fpsmaster.FPSMaster;
import top.fpsmaster.core.I18N.I18NUtils;
import top.fpsmaster.gui.element.ButtonComponent;
import top.fpsmaster.gui.font.FontLoader;
import top.fpsmaster.gui.font.UFontRenderer;
import top.fpsmaster.utils.render.GuiInputField;
import top.fpsmaster.utils.render.RenderUtil;

import java.io.IOException;
import java.util.UUID;

public class GuiOfflineLogin extends GuiScreen {
    private final GuiScreen previousScreen;
    private final UFontRenderer cFont = FontLoader.getCFont(true, 24);
    private GuiInputField usernameTextBox;

    public GuiOfflineLogin(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        this.usernameTextBox = new GuiInputField(width / 2.0f - 140, height / 2.0f - 66, 272, 20);
        super.initGui();
    }

    ButtonComponent button = new ButtonComponent(0, 0, 60, 20, 20, () -> {
        mc.session = new Session(usernameTextBox.getText(), UUID.randomUUID().toString(), "0", "mojang");
        mc.displayGuiScreen(new GuiMainMenu());
    }, "Login", FPSMaster.INSTANCE.theme.getPrimary(), true);

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int width = sr.getScaledWidth();
        int height = sr.getScaledHeight();
        RenderUtil.drawImage(new ResourceLocation("client/bg.png"), 0, 0, width, height);
        RenderUtil.drawRoundedRect((float) width / 2 - 150, (float) height / 2 - 100, (float) width / 2 + 150, (float) height / 2 + 100, 2, FPSMaster.INSTANCE.theme.getTertiary().getRGB());
        this.cFont.drawString(I18NUtils.getString("ol.login"), (float) width / 2 - 138, height / 2 - 82, FPSMaster.INSTANCE.theme.getOffline().getRGB());
        this.usernameTextBox.draw(mouseX, mouseY);
        button.display((float) width / 2 - 120, (float) height / 2 - 40, mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.usernameTextBox.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.usernameTextBox.mouseClicked(mouseX, mouseY, mouseButton);
        button.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
        button.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.usernameTextBox.update();
        super.updateScreen();
    }
}
