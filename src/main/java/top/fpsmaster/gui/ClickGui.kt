package top.fpsmaster.gui

import me.superskidder.elementUI.impl.Component
import me.superskidder.elementUI.layout.Alignment
import me.superskidder.elementUI.layout.ScrollableLayout
import me.superskidder.elementUI.layout.gird.ColumnDefinitions
import me.superskidder.elementUI.layout.gird.RowDefinitions
import net.minecraft.client.gui.GuiScreen
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import top.fpsmaster.FPSMaster
import top.fpsmaster.core.I18N.I18NUtils
import top.fpsmaster.core.ModuleCategory
import top.fpsmaster.gui.element.ImageComponent
import top.fpsmaster.gui.element.LabelComponent
import top.fpsmaster.gui.element.PanelComponent
import top.fpsmaster.gui.element.data.ModuleComponent
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.gui.guiScreen.GuiEditCustom
import top.fpsmaster.gui.notification.Notification
import top.fpsmaster.modules.settings.ClientSettings
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color
import java.io.IOException

class ClickGui(private val doesGuiPauseGame: Boolean) : GuiScreen() {
    private var drag = false
    private var dragX = 0f
    private var dragY = 0f
    private var theme = FPSMaster.INSTANCE.theme
    private var curType = ModuleCategory.Renders
    private var sizeDrag = false
    private var sizeDragX = 0f
    private var sizeDragY = 0f
    private var leftWidth = 100f
    private var openAnimation = 60f
    private val guiOpenAnimation = AnimationUtils()

    init {
        theme = FPSMaster.INSTANCE.theme
        // 初始化Mod List
        if (ClientSettings.debug.value || panel.elements.size == 0) {
            moduleBox.elements.clear()
            for ((i, value) in ModuleCategory.values().withIndex()) {
                anchors.add(
                    LabelComponent(
                        0f,
                        1f,
                        0f,
                        0f,
                        I18NUtils.getString("type." + value.name),
                        24,
                        theme.quaternary.rgb,
                        true
                    ).setId(value.name)
                )
                moduleBox.addElement(anchors[i])
                for (module in FPSMaster.INSTANCE.moduleManager?.modules?.values!!) {
                    if (module.type == value) {
                        moduleBox.addElement(ModuleComponent(module))
                    }
                }
            }
            panel.elements.clear()
            // 初始化主面板
            panel.addElements(
                RowDefinitions(30, 0f, false).addElement(
                    ImageComponent(
                        10,
                        10,
                        233 / 2,
                        32 / 2,
                        "client/guis/clickgui/logo.png",
                        Color(255,255,255)
                    )
                ),
                RowDefinitions("100", 0f, false).addElements(
                    ColumnDefinitions("20", 0f, false),
                    ColumnDefinitions("100", 0f, false).addElement(moduleBox)
                )
            )
        }

    }




    override fun doesGuiPauseGame(): Boolean {
        return doesGuiPauseGame
    }

    override fun onGuiClosed() {
        super.onGuiClosed()
        FPSMaster.INSTANCE.moduleManager!!.modules["ClickGui"]!!.setStage(false)
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.drawScreen(mouseX, mouseY, partialTicks)
        sizeAnimate(x, y, panel.width, panel.height, openAnimation / 100)

        RenderUtil.drawRoundRect10(x, y, Companion.width, Companion.height, theme.tertiary)

        RenderUtil.drawImage(
            ResourceLocation("client/guis/clickgui/drag.png"),
            x + Companion.width - 10,
            y + Companion.height - 10,
            8f,
            8f,
            theme.quaternary
        )

        RenderUtil.drawRoundRect10(x, y + Companion.height - 20, leftWidth, 20f, theme.primary)
        RenderUtil.drawImage(
            ResourceLocation("client/guis/clickgui/custom.png"),
            x + 10,
            y + Companion.height - 16,
            23f / 2,
            23f / 2,
            theme.tertiary
        )
        FontLoader.getCFont(false, 18)
            .drawCenteredString(
                I18NUtils.getString("clickgui.custom"),
                x + leftWidth / 2,
                y + Companion.height - 14,
                theme.tertiary.rgb
            )


        if (!Mouse.isButtonDown(0)) {
            drag = false
            sizeDrag = false
        }
        val sr = ScaledResolution(mc)
        openAnimation = guiOpenAnimation.animate(100f, openAnimation, 0.15f, false)
        if (drag) {
            if (mouseX - dragX > 0 && mouseX - dragX < sr.scaledWidth - Companion.width || x + Companion.width > sr.scaledWidth && mouseX - dragX < x) {
                x = mouseX - dragX
            }
            if (mouseY - dragY > 0 && mouseY - dragY < sr.scaledHeight - Companion.height || y + Companion.height > sr.scaledHeight && mouseY - dragY < y) {
                y = mouseY - dragY
            }
        }
        val w = mouseX + sizeDragX - x
        val h = mouseY + sizeDragY - y
        if (sizeDrag) {

            if (w > 400 || w > Companion.width) {
                Companion.width = w
            }
            if (h > 200 || h > Companion.height) {
                Companion.height = h
            }
        }
        panel.width = (Companion.width)
        panel.height = (Companion.height)
        leftWidth = Companion.width * 0.2f
        moduleBox.xLimit = (x + leftWidth)
        moduleBox.yLimit = (y + 30)
        moduleBox.widthLimit = (Companion.width - leftWidth)
        moduleBox.heightLimit = (Companion.height - 40) * (openAnimation / 100f)
//        RenderUtil.drawRoundRect10(x, y, Companion.width, Companion.height, theme.tertiary)
        panel.display(x, y, mouseX.toFloat(), mouseY.toFloat())

        RenderUtil.drawImage(
            ResourceLocation("client/guis/clickgui/drag.png"),
            x + Companion.width - 10,
            y + Companion.height - 10,
            8f,
            8f,
            theme.quaternary
        )

//        绘制categories
        var my = y + 60
        for (m in ModuleCategory.values()) {
            if (curType == m) {
                FontLoader.getCFont(true, 20).drawCenteredString(
                    I18NUtils.getString("type." + m.name),
                    x + leftWidth / 2,
                    my,
                    if (curType == m) theme.primary.rgb else theme.quaternary.rgb
                )
            } else {
                FontLoader.getCFont(false, 20).drawCenteredString(
                    I18NUtils.getString("type." + m.name),
                    x + leftWidth / 2,
                    my,
                    if (curType == m) theme.primary.rgb else theme.quaternary.rgb
                )
            }
            my += 26f
        }

        //mods列表滑动
        val mouseDWheel = Mouse.getDWheel()
        if (mouseDWheel != 0) {
            panel.mouseScrolled(mouseDWheel)
        }
        // type更新
        for (anchor in anchors) {
            if (anchor.dY <= y + 41 + anchor.height) {
                curType = ModuleCategory.valueOf(anchor.id)
            }
        }
    }

    override fun initGui() {
        super.initGui()
        val sr = ScaledResolution(mc)
        theme = FPSMaster.INSTANCE.theme
        if (Companion.width > sr.scaledWidth) {
            x = 0f
            Companion.width = sr.scaledWidth.toFloat()
        }
        if (Companion.height > sr.scaledHeight) {
            y = 0f
            Companion.height = sr.scaledHeight.toFloat()
        }

    }

    @Throws(IOException::class)
    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (keyCode == 1) {
            mc.displayGuiScreen(null)
            FPSMaster.INSTANCE.notificationsManager?.add(Notification("ClickGui Disabled", Notification.Type.Info))
            if (mc.currentScreen == null) {
                mc.setIngameFocus()
            }
        }
        moduleBox.keyTyped(typedChar,keyCode)
    }

    @Throws(IOException::class)
    override fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        super.mouseClicked(mouseX, mouseY, mouseButton)
        if (mouseButton == 0 && isHovered(
                x,
                y + Companion.height - 20,
                x + leftWidth,
                y + Companion.height,
                mouseX,
                mouseY
            )
        ) {
            mc.displayGuiScreen(GuiEditCustom())
        }
        if (mouseButton == 0 && isHovered(
                x + 10,
                y + Companion.height - 16,
                x + 10 + 23f / 2,
                y + Companion.height - 16 + 23f / 2, mouseX, mouseY
            )
        ) {
            mc!!.displayGuiScreen(GuiEditCustom())
        } else if (mouseButton == 0 && isHovered(x, y, x + Companion.width, y + 30, mouseX, mouseY)) {
            drag = true
            dragX = mouseX - x
            dragY = mouseY - y
        } else if (mouseButton == 0 && isHovered(
                x + Companion.width - 12,
                y + Companion.height - 12,
                x + Companion.width + 4,
                y + Companion.height + 4,
                mouseX,
                mouseY
            )
        ) {
            sizeDrag = true
            sizeDragX = x + Companion.width - mouseX
            sizeDragY = y + Companion.height - mouseY
        }
        moduleBox.mouseClicked(mouseX.toFloat(), mouseY.toFloat(), mouseButton)
        var my = y + 60
        for ((i, anchor) in anchors.withIndex()) {
            if (isHovered(
                    x,
                    my - 5,
                    x + 79,
                    my + FPSMaster.INSTANCE.fontLoader?.arial18!!.height + 5,
                    mouseX,
                    mouseY
                )
            ) {
                curType = ModuleCategory.valueOf(anchor.id)
                moduleBox.scrollF += y + 35 - anchors[i].dY
                break
            }
            my += 26f
        }
    }

    companion object {
        var x = -1f
        var y = -1f
        var width = 500f
        var height = 300f
        private val panel = PanelComponent(width, height, true)
        private val moduleBox = ScrollableLayout(0f, 0f, 0f, 0f, Alignment.Vertical, 5f, true)
        private var anchors = ArrayList<Component>()

        fun sizeAnimate(x: Float, y: Float, width: Float, height: Float, progress: Float) {
            GL11.glScaled(progress.toDouble(), progress.toDouble(), 1.0)
            val xpos = (x + width / 2 / progress) * (1 - progress)
            val ypos = (y + height / 2 / progress) * (1 - progress)
            GL11.glTranslated(xpos.toDouble(), ypos.toDouble(), 0.0)
        }
    }
}