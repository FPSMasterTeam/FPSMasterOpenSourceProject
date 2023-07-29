package top.fpsmaster.gui.guiScreen

import net.minecraft.client.gui.GuiScreen
import top.fpsmaster.FPSMaster
import java.io.IOException

class GuiEditCustom : GuiScreen() {
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
        FPSMaster.INSTANCE.guiCustom!!.mouseRelease(mouseX, mouseY)
        FPSMaster.INSTANCE.guiCustom!!.drawScreen(mouseX, mouseY)
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        FPSMaster.INSTANCE.guiCustom!!.mouseClicked(mouseX, mouseY, mouseButton)
    }
}