package top.fpsmaster.gui.element

import net.minecraft.util.ResourceLocation
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.gui.font.UFontRenderer
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

class MenuIconComponent(x: Int, y: Int, width: Int, height: Int, image: String, var tag: String, var run: Runnable) :
    ImageComponent(x, y, width, height, image,Color(0,0,0)) {
    var animY = 0f
    var animationUtils = AnimationUtils()
    var cFont: UFontRenderer
    var iWidth: Float
    var iHeight: Float

    init {
        iWidth = width.toFloat()
        iHeight = height.toFloat()
        cFont = FontLoader.getCFont(true, 16)
        hoverAppend = 8f
    }

    override fun mouseClicked(x: Float, y: Float, btn: Int) {
        super.mouseClicked(x, y, btn)
        run.run()
    }

    override fun render(x: Float, y: Float) {
        RenderUtil.drawImage(
            ResourceLocation(image),
            x,
            y,
            iWidth,
            iHeight,
            if (hovered) theme.primary else theme.quaternary
        )
        cFont.drawCenteredString(
            tag,
            x + iWidth / 2 + 0.5f,
            y + iHeight + 10 + animY * 4 + 0.5f,
            Color(100, 100, 100, Math.abs(animY * 100).toInt()).rgb
        )
        cFont.drawCenteredString(
            tag,
            x + iWidth / 2,
            y + iHeight + 10 + animY * 4,
            Color(255, 255, 255, Math.abs(animY * 200).toInt()).rgb
        )
        animY = if (hovered) {
            animationUtils.animate(1f, animY, 0.3f)
        } else {
            animationUtils.animate(0f, animY, 0.3f)
        }
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
    }
}