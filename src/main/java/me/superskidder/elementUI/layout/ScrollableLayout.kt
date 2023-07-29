package me.superskidder.elementUI.layout

import org.lwjgl.opengl.GL11
import top.fpsmaster.FPSMaster
import top.fpsmaster.data.Theme
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.render.RenderUtil

class ScrollableLayout : FlowLayout {
    var scroll = 0f
    var animationUtils = AnimationUtils()
    var scrollF = 0f

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        alignment: Alignment,
        gutter: Float,
        animation: Boolean
    ) : super(x, y, width, height, alignment, gutter, animation) {
        widthLimit = width
        heightLimit = height
    }

    constructor(alignment: Alignment, gutter: Float, animation: Boolean) : super(alignment, gutter, animation)
    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        alignment: Alignment,
        gutter: Float,
        animation: Boolean,
        weightLimit: Float,
        heightLimit: Float
    ) : super(x, y, width, height, alignment, gutter, animation, weightLimit, heightLimit)

    override fun mouseRelease(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.mouseRelease(x, y, mouseX, mouseY)
    }

    override fun display(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        RenderUtil.doGlScissor(xLimit, yLimit, widthLimit, heightLimit)
        super.display(x, y + scroll, mouseX, mouseY)

        //滑动条
        val scrollHeight = heightLimit / height * (heightLimit - 10)
        val scrollY = yLimit + -scroll / (-scroll + heightLimit) * heightLimit
        RenderUtil.drawRect(
            xLimit + widthLimit - 3,
            scrollY,
            xLimit + widthLimit - 2,
            scrollY + scrollHeight,
            Theme.setBrightness(FPSMaster.INSTANCE.theme.quaternary, 0.5f).rgb
        )
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
        scroll = animationUtils.animate(scrollF, scroll, 0.12f)
    }

    override fun mouseScrolled(dWheel: Int) {
        super.mouseScrolled(dWheel)
        if (dWheel > 0 && scrollF + 32 <= 0) scrollF += 32f else if (dWheel < 0 && scrollF >= -(height - 50)) scrollF -= 32f
        if (scrollF + 32 > 0) {
            scrollF = 0f
        }
    }
}