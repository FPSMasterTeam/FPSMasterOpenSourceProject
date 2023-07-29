package top.fpsmaster.gui.element.data

import me.superskidder.elementUI.layout.Alignment
import me.superskidder.elementUI.layout.BasicLayout
import me.superskidder.elementUI.layout.FlowLayout
import net.minecraft.util.ResourceLocation
import top.fpsmaster.core.I18N.I18NUtils
import top.fpsmaster.core.Module
import top.fpsmaster.core.values.values.BooleanValue
import top.fpsmaster.core.values.values.ColorValue
import top.fpsmaster.core.values.values.ModeValue
import top.fpsmaster.core.values.values.NumberValue
import top.fpsmaster.core.values.values.TextValue
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.math.ColorAnimationUtils
import top.fpsmaster.utils.math.ColorUtils
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

class ModuleComponent(var mod: Module) : BasicLayout() {
    private var wrapLayout: FlowLayout = FlowLayout(0f, 0f, 0f, 0f, Alignment.Horizontal, 20f, true)
    private var flowLayout: FlowLayout = FlowLayout(0f, 4f, 0f, 0f, Alignment.Vertical, 10f, true)


    init {
        wrapLayout.forceWrap = true
        flowLayout.addElement(wrapLayout)
        for (v in mod.values) {
            when (v) {
                is BooleanValue -> {
                    wrapLayout.addElement(BooleanComponent(0f, 0f, 0f, 12f, "mod.${mod.name}.${v.name}", v))
                }

                is NumberValue<*> -> {
                    wrapLayout.addElement(NumberComponent(0f, 0f, 100f, 12f, "mod.${mod.name}.${v.name}", v))
                }

                is ModeValue -> {
                    flowLayout.addElement(ModeComponent(0f, 0f, 100f, 20f, "mod.${mod.name}.${v.name}", v))
                }

                is ColorValue -> {

                }

                is TextValue -> {
                    flowLayout.addElement(TextComponent(v, 140f, 16f))
                }
            }
        }

        if (wrapLayout.elements.size == 0)
            flowLayout.removeElement(wrapLayout)
    }

    var expand = false
    private var expandY: Float = 0f
    val animationUtils = AnimationUtils()
    val knobAnim = AnimationUtils()
    private val knobColorAnim = ColorAnimationUtils()
    private var knobColor: Color = Color(255, 255, 255)
    private var knobProgress: Float = 0f


    override fun display(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.display(x, y, mouseX, mouseY)
        val ry = y + this.y
        val rx = x + this.x
        RenderUtil.drawBordered(
            rx.toDouble(), ry.toDouble(), width.toDouble(), height.toDouble(),
            0.5, Color(255, 255, 255).rgb, Color(230, 230, 230).rgb
        )
        if (mod.canBeEnabled) {
            RenderUtil.drawImage(
                ResourceLocation("client/guis/clickgui/track.png"),
                x + width - 30,
                y + 15 - 3.5f,
                17f,
                7f,
                Color(knobColor.red, knobColor.green, knobColor.blue, 100)
            )
            RenderUtil.drawImage(
                ResourceLocation("client/guis/clickgui/knob.png"),
                x + width - 32 + 12 * (knobProgress / 100f),
                y + 15 - 5f,
                10f, 10f,
                knobColor
            )
        }


        FontLoader.getCFont(false, 18)
            .drawString(I18NUtils.getString("mod.${mod.name}"), this.x + x + 6, y + 8, theme.quaternary.rgb)
        FontLoader.getCFont(false, 18).drawString(
            I18NUtils.getString("mod.${mod.name}.desc"),
            rx + 6,
            ry + 16,
            ColorUtils.darker(theme.tertiary, 0.2).rgb
        )
        flowLayout.xLimit = this.x + x
        flowLayout.widthLimit = this.width
        flowLayout.yLimit = this.y + y + 30
        flowLayout.heightLimit = this.expandY
        flowLayout.parent = this
        flowLayout.update(this.x + x + 10, this.y + y + 34, mouseX, mouseY)

        if (expandY > 0) {
            RenderUtil.drawRect(
                this.x + x,
                this.y + y + 30f,
                this.x + x + this.width * (expandY / (flowLayout.height + 3)),
                this.y + y + 30.5f,
                ColorUtils.reAlpha(theme.quaternary.rgb, 0.3f)
            )
            flowLayout.display(this.x + x + 10, this.y + y + 34, mouseX, mouseY)
            RenderUtil.drawRect(
                this.x + x,
                ry + this.height + 1,
                this.x + x + this.width,
                ry + this.height + flowLayout.height + 1,
                theme.tertiary.rgb
            )
        }
    }


    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
        this.width = parent!!.parent!!.width - 15
        this.x = 5f
        this.height = 34 + expandY
        expandY = if (expand)
            animationUtils.animate(flowLayout.height + 3, expandY, 0.2f)
        else
            animationUtils.animate(0f, expandY, 0.2f)
        knobProgress = if (mod.stage)
            knobAnim.animate(100f, knobProgress, 0.2f)
        else
            knobAnim.animate(0f, knobProgress, 0.2f)

        knobColor = if (mod.stage)
            knobColorAnim.animate(theme.primary, knobColor, 0.2f)
        else
            knobColorAnim.animate(
                Color(100, 100, 100),
                knobColor,
                0.2f
            )

    }


    override fun keyTyped(char: Char, int: Int) {
        super.keyTyped(char, int)
        flowLayout.keyTyped(char, int)
    }

    override fun mouseClicked(x: Float, y: Float, btn: Int) {
        super.mouseClicked(x, y, btn)
        flowLayout.mouseClicked(x, y, btn)
        if (y < this.y + this.dY + 30)
            if (btn == 1 && mod.values.size > 0)
                expand = !expand;
            else if (btn == 0)
                mod.setStage(!mod.stage)

    }
}