package top.fpsmaster.gui.element

import lombok.Getter
import lombok.Setter
import me.superskidder.elementUI.impl.Component
import top.fpsmaster.gui.font.FontLoader
import top.fpsmaster.gui.font.UFontRenderer

@Getter
@Setter
class LabelComponent : Component {
    var text: String
    var color: Int
    var fontsize: Int
    var bold: Boolean
    var cFont: UFontRenderer

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        str: String,
        fontsize: Int,
        color: Int,
        bold: Boolean
    ) : super(x, y, width, height) {
        text = str
        this.fontsize = fontsize
        this.color = color
        this.bold = bold
        if (bold) this.y += 2f
        cFont = FontLoader.getCFont(bold, fontsize)
    }

    constructor(s: String) : super(0f, 0f, 0f, 0f) {
        text = s
        fontsize = 20
        color = -0x1
        bold = false
        cFont = FontLoader.getCFont(bold, fontsize)
    }

    override fun render(x: Float, y: Float) {
        super.render(x, y)
        cFont.drawString(text, x, y, color)
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
        fWidth = (cFont.getStringWidth(text) + 4).toFloat()
        fHeight = cFont.height + 4
    }
}