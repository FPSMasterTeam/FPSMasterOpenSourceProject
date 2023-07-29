package top.fpsmaster.gui.element.data

import me.superskidder.elementUI.impl.Component
import net.minecraft.util.ResourceLocation
import top.fpsmaster.FPSMaster
import top.fpsmaster.core.I18N.I18NUtils
import top.fpsmaster.core.values.values.BooleanValue
import top.fpsmaster.data.Theme.Companion.setBrightness
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.gui.font.UFontRenderer
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.math.ColorAnimationUtils
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

class BooleanComponent(x: Float, y: Float, width: Float, height: Float, private var name: String, value: BooleanValue?) :
    Component(x, y, width, height) {
    var value = false
    var bvalue: BooleanValue? = value
    var runnable: Runnable? = null
    var type = 0 // 0->circle 1->round rectangle
    var hoverValue = 0f
    var hoverValueA = AnimationUtils()

    var cFont: UFontRenderer

    val colorAnimationUtils = ColorAnimationUtils()
    var option_color = Color(255, 255, 255);

    override fun render(x: Float, y: Float) {
        super.render(x, y)
        if (bvalue != null) {
            value = bvalue!!.value
        }

        RenderUtil.drawRect(
            (x - 2).toDouble(),
            (y).toDouble(),
            (x + width + 2).toDouble(),
            (y + height).toDouble(),
            Color(220, 220, 220, (hoverValue * 100).toInt())
        )
        if (value) {
            RenderUtil.drawImage(
                ResourceLocation("client/guis/clickgui/option_on.png"),
                x,
                y + 4,
                8f,
                8f,
                option_color
            )
        } else {
            RenderUtil.drawImage(
                ResourceLocation("client/guis/clickgui/option_off.png"),
                x,
                y + 4,
                8f,
                8f,
                option_color
            )
        }
        cFont.drawString(I18NUtils.getString(name), x + 10, y + 4, option_color.rgb)
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
        hoverValue = if (hovered) {
            hoverValueA.animate(1.2f, hoverValue, 0.1f)
        } else {
            hoverValueA.animate(0f, hoverValue, 0.1f)
        }
        width = (20 + cFont.getStringWidth(name)).toFloat()
        height = cFont.height + 8

        option_color = if (bvalue!!.value) {
            colorAnimationUtils.animate(theme.primary, option_color, 0.1f)
        } else {
            colorAnimationUtils.animate(setBrightness(FPSMaster.INSTANCE.theme.tertiary, 0.7f), option_color, 0.1f)
        }
    }

    override fun mouseClicked(x: Float, y: Float, btn: Int) {
        super.mouseClicked(x, y, btn)
        if (btn == 0) {
            if (runnable != null) {
                runnable!!.run()
            } else {
                bvalue!!.value = !bvalue!!.value
            }
        }
    }

    init {
        type = 0
        cFont = FontLoader.getCFont(false, 18)
    }
}