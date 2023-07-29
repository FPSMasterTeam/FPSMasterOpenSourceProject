package top.fpsmaster.gui.element

import me.superskidder.elementUI.impl.Component
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

class ButtonComponent(
    x: Int,
    y: Int,
    width: Float,
    height: Float,
    fontSize: Int,
    run: Runnable,
    var text: String,
    var color: Color,
    var bold: Boolean
) :
    Component(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat()) {
    var task: Runnable
    var size: Int

    init {
        this.width = width
        this.height = height
        task = run
        size = fontSize;
    }

    override fun display(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.display(x, y, mouseX, mouseY)
        this.x = x
        this.y = y
        if (isHovered(x, y, x + width, y + height, x, y)) {
            RenderUtil.drawRoundRect10(x, y, width, height, color)
        } else {
            RenderUtil.drawRoundRect10(x, y, width, height, color)
        }
        FontLoader.getCFont(bold, size)
            .drawCenteredString(text, x + width / 2, y + height / 2 - 4, -1)
    }

    override fun mouseClicked(x: Float, y: Float, btn: Int) {
        super.mouseClicked(x, y, btn)
        if (isHovered(this.x, this.y, this.x + width, this.y + height, x, y) && btn == 0) {
            task.run()
        }
    }

    fun isHovered(x: Float, y: Float, x2: Float, y2: Float, mouseX: Float, mouseY: Float): Boolean {
        return mouseX in x..x2 && mouseY >= y && mouseY <= y2
    }
}