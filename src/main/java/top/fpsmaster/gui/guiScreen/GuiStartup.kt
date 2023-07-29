package top.fpsmaster.gui.guiScreen

import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import org.lwjgl.input.Mouse
import top.fpsmaster.FPSMaster
import top.fpsmaster.core.I18N.I18NUtils
import top.fpsmaster.data.ConfigManager
import top.fpsmaster.data.Theme
import top.fpsmaster.gui.classicComponents.RoundButton
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color
import java.io.File
import java.io.IOException
import java.nio.file.Files
import javax.swing.JFileChooser
import javax.swing.UIManager
import javax.swing.filechooser.FileNameExtensionFilter

class GuiStartup (private val needScreen : GuiScreen) : GuiScreen() {
    private var btnDone: RoundButton? = null
    private var btnImport: RoundButton? = null
    private var selected: String = ""
    var theme: Theme? = null
    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
        drawRect(0f, 0, sr!!.scaledWidth.toFloat(), sr!!.scaledHeight, Color(245, 245, 245).rgb)
        RenderUtil.drawShadow(
            sr!!.scaledWidth / 2f - 100,
            (sr!!.scaledHeight - FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2f,
            sr!!.scaledWidth / 2f + 100,
            (sr!!.scaledHeight + FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2f,
            6
        )
        RenderUtil.drawRoundedRect(
            sr!!.scaledWidth / 2f - 100,
            (sr!!.scaledHeight - FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2f,
            sr!!.scaledWidth / 2f + 100,
            (sr!!.scaledHeight + FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2f,
            3f,
            theme!!.language_bg.rgb
        )
        FPSMaster.INSTANCE.fontLoader!!.client18.drawString(
            "Select Language / 选择语言",
            sr!!.scaledWidth / 2f - 100,
            (sr!!.scaledHeight - FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2 - 15,
            Color.BLACK.rgb
        )
        var lY = (sr!!.scaledHeight - FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2f + 5
        for (s in FPSMaster.INSTANCE.configManager!!.supported) {
            if (s == selected) {
                RenderUtil.drawRoundedRect(
                    sr!!.scaledWidth / 2f - 100,
                    lY - 5,
                    sr!!.scaledWidth / 2f + 100,
                    lY + 15,
                    3f,
                    theme!!.primary.rgb
                )
                FPSMaster.INSTANCE.fontLoader!!.client18.drawCenteredString(
                    s,
                    sr!!.scaledWidth / 2f,
                    lY,
                    theme!!.language_text_sel.rgb
                )
            } else {
                if (isHovered(
                        sr!!.scaledWidth / 2f - 100,
                        lY - 5,
                        (sr!!.scaledWidth + 100).toFloat(),
                        lY + 14,
                        mouseX,
                        mouseY
                    )
                ) {
                    RenderUtil.drawRoundedRect(
                        sr!!.scaledWidth / 2f - 100,
                        lY - 5,
                        sr!!.scaledWidth / 2f + 100,
                        lY + 15,
                        3f,
                        theme!!.language_sel.rgb
                    )
                }
                FPSMaster.INSTANCE.fontLoader!!.client18.drawCenteredString(
                    s,
                    sr!!.scaledWidth / 2f,
                    lY,
                    theme!!.language_text_unsel.rgb
                )
            }
            lY += 20f
        }
        btnDone!!.drawButton()
        btnImport!!.drawButton()
        btnDone!!.mouseReleased(mouseX, mouseY)
        btnImport!!.mouseReleased(mouseX, mouseY)
    }

    var sr: ScaledResolution? = null
    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        btnDone!!.mouseClicked(mouseX, mouseY, mouseButton)
        btnImport!!.mouseClicked(mouseX, mouseY, mouseButton)
        var lY = (sr!!.scaledHeight - FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2.0f + 5
        for (s in FPSMaster.INSTANCE.configManager!!.supported) {
            if (isHovered(
                    sr!!.scaledWidth / 2.0f - 100,
                    lY - 5,
                    (sr!!.scaledWidth + 100).toFloat(),
                    lY + 14,
                    mouseX,
                    mouseY
                ) && Mouse.isButtonDown(0)
            ) {
                I18NUtils.loadLanguage(s)
                selected = s
                btnDone!!.string = I18NUtils.getString("language.done")
                btnImport!!.string = I18NUtils.getString("language.import")
            }
            lY += 20f
        }
    }

    override fun initGui() {
        super.initGui()
        theme = FPSMaster.INSTANCE.theme
        FPSMaster.INSTANCE.configManager!!.reloadLanguages()
        selected = ConfigManager.language
        sr = ScaledResolution(mc)
        I18NUtils.loadLanguage(selected)
        btnImport = RoundButton(
            I18NUtils.getString("language.import"),
            sr!!.scaledWidth / 2f - 100,
            (sr!!.scaledHeight + FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2f + 10,
            90f,
            20f,
            theme!!.primary,
            theme!!.language_bg,
            Color.WHITE,
            Color.BLACK,
            Runnable {
                var path: String? = null
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
                } catch (e: Exception) {
                    FPSMaster.INSTANCE.logger.info("Import Failed" + e.message)
                }
                val jdir = JFileChooser()
                jdir.fileSelectionMode = JFileChooser.FILES_ONLY
                val filter = FileNameExtensionFilter(
                    "语言配置文件 / Language File (*.json)", "json"
                )
                jdir.fileFilter = filter
                jdir.dialogTitle = "请选择语言文件 / Please select Language file"
                if (JFileChooser.APPROVE_OPTION == jdir.showOpenDialog(null)) {
                    path = jdir.selectedFile.absolutePath
                }
                if (path == null) {
                    return@Runnable
                }
                val target = File(ConfigManager.dir + "/languages/" + File(path).name).absoluteFile
                try {
                    Files.copy(File(path).toPath(), target.toPath())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                FPSMaster.INSTANCE.configManager!!.reloadLanguages()
                selected = target.name.replace(".json".toRegex(), "")
            },
            false
        )
        btnDone = RoundButton(
            I18NUtils.getString("language.done"),
            sr!!.scaledWidth / 2f + 10,
            (sr!!.scaledHeight + FPSMaster.INSTANCE.configManager!!.supported.size * 20) / 2f + 10,
            90f,
            20f,
            theme!!.primary,
            theme!!.language_bg,
            Color.WHITE,
            Color.BLACK,
            {
                ConfigManager.language = selected
                FPSMaster.INSTANCE.configManager!!.toggle("startup")
                mc.displayGuiScreen(needScreen)
            },
            false
        )
    }

    override fun onGuiClosed() {
        super.onGuiClosed()
    }
}