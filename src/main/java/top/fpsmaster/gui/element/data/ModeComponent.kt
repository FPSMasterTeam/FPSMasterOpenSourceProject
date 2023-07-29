package top.fpsmaster.gui.element.data

import me.superskidder.elementUI.impl.Component
import top.fpsmaster.FPSMaster
import top.fpsmaster.core.I18N.I18NUtils
import top.fpsmaster.core.values.values.ModeValue
import top.fpsmaster.data.Theme.Companion.setBrightness
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.gui.font.UFontRenderer
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.render.RenderUtil

class ModeComponent(x: Float, y: Float, width: Float, height: Float, private var name: String, var value: ModeValue) :
    Component(x, y, width, height) {
    var cFont: UFontRenderer
    private var expand = false
    var animationUtils = AnimationUtils()
    var mouseX = 0f
    var mouseY = 0f

    init {
        cFont = FontLoader.getCFont(false, 18)
    }

    override fun render(x: Float, y: Float) {
        var x1 = x;
        super.render(x1, y)
        val current = value.current
        cFont.drawString(I18NUtils.getString(name), x1, y + 2f, setBrightness(FPSMaster.INSTANCE.theme.tertiary, 0.7f).rgb)
        x1 += (cFont.getStringWidth(I18NUtils.getString(name)) + 4).toFloat()
        var width: Float
        val stringWidth1 = cFont.getStringWidth(current)
        width = (stringWidth1 + 16).toFloat()
        for (mode in value.modes) {
            val stringWidth = cFont.getStringWidth(mode)
            if (stringWidth + 16 > width) width = (stringWidth + 16).toFloat()
        }
        RenderUtil.drawBordered(
            x1.toDouble(),
            y.toDouble(),
            width.toDouble(),
            this.height.toDouble(),
            0.5,
            setBrightness(FPSMaster.INSTANCE.theme.tertiary, 1f).rgb,
            setBrightness(FPSMaster.INSTANCE.theme.tertiary, 0.8f).rgb
        )
        if (expand)
            RenderUtil.drawRect(
                x1.toDouble(),
                y + 11.5,
                x1 + width.toDouble(),
                y + 12.0,
                setBrightness(FPSMaster.INSTANCE.theme.tertiary, 0.8f).rgb
            )
        cFont.drawString(
            current,
            x1 + 2,
            y + 1,
            setBrightness(FPSMaster.INSTANCE.theme.tertiary, 0.6f).rgb
        )
        if (expand) {
            var i = 0
            for (mode in value.modes) {
                if (mode != value.current) {
                    cFont.drawString(
                        mode,
                        x1 + 4,
                        y + 12 * i + 14,
                        setBrightness(FPSMaster.INSTANCE.theme.tertiary, 0.5f).rgb
                    )
                    i++
                }
            }
        }
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)

        var width: Float
        val stringWidth1 = cFont.getStringWidth(value.current)
        width = (stringWidth1 + 10).toFloat()
        for (mode in value.modes) {
            val stringWidth = cFont.getStringWidth(mode)
            if (stringWidth + 10 > width) width = (stringWidth + 10).toFloat()
        }
        val height = (value.modes.size * 12).toFloat()
        this.width = stringWidth1 + 4 + width + 5 + 10 + (cFont.getStringWidth(value.name) + 4).toFloat()
//        width = parent!!.widthLimit - 10
        if (expand) {
            this.height = animationUtils.animate(height, this.height, 0.2f)
        } else {
            this.height = animationUtils.animate(12f, this.height, 0.2f)
        }
        this.mouseX = mouseX
        this.mouseY = mouseY
    }

    fun isHovered(x: Float, y: Float, width: Float, height: Float, mouseX: Float, mouseY: Float): Boolean {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height
    }

    override fun mouseClicked(x: Float, y: Float, btn: Int) {
        super.mouseClicked(x, y, btn)
        if (btn == 0) {
            if (expand) {
                var i = 0
                for (mode in value.modes) {
                    if (mode != value.current) {
                        if (isHovered(dX, dY + 12 * i + 12, width, 12f, mouseX, mouseY)) {
                            value.current = mode
                            expand = false
                        }
                        i++
                    }
                }
            }
            if (isHovered(dX, dY, width, 12f, mouseX, mouseY)) {
                expand = !expand
            }
        }
    }
}