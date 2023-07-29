package top.fpsmaster.gui.element

import me.superskidder.elementUI.impl.Component
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.gui.font.UFontRenderer
import top.fpsmaster.utils.render.RenderUtil

class TagComponent(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    var bgColor: Int,
    var tagColor: Int,
    padding: Float,
    tag: String,
    fontsize: Int
) : Component(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat()) {
    private var padding = 0f
    private val fontsize: Int
    private val tag: String
    var cFont: UFontRenderer

    init {
        this.padding = padding
        this.tag = tag
        this.fontsize = fontsize
        cFont = FontLoader.getCFont(true, fontsize)
    }

    override fun render(x: Float, y: Float) {
        super.render(x, y)
        RenderUtil.drawRoundedRect2(x - padding, y - padding, x + width + padding, y + height + padding, bgColor)
        cFont.drawString(tag, x + 3, y + 2, tagColor)
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
        width = FontLoader.getCFont(false, fontsize).getStringWidth(tag) + padding * 2 + 10
    }
}