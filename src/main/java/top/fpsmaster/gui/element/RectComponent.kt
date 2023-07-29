package top.fpsmaster.gui.element

import me.superskidder.elementUI.impl.Component
import top.fpsmaster.utils.render.RenderUtil

class RectComponent : Component {
    var color: Int
    private var padding = 0f
    private val type: RectangleType
    private var radius = 2

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        color: Int,
        padding: Float,
        type: RectangleType
    ) : super(x, y, width, height) {
        this.color = color
        this.padding = padding
        this.type = type
    }

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        color: Int,
        padding: Float,
        type: RectangleType,
        radius: Int
    ) : super(x, y, width, height) {
        this.color = color
        this.padding = padding
        this.type = type
        this.radius = radius
    }

    override fun render(x: Float, y: Float) {
        super.render(x, y)
        when (type) {
            RectangleType.ROUND -> RenderUtil.drawRoundedRect(
                x - padding,
                y - padding,
                x + width + padding,
                y + height + padding,
                radius.toFloat(),
                color
            )

            RectangleType.NORMAL -> RenderUtil.drawRect(
                x - padding,
                y - padding,
                x + width + padding,
                y + height + padding,
                color
            )

            RectangleType.CIRCLE -> RenderUtil.drawRoundedRect2(
                x - padding,
                y - padding,
                x + width + padding,
                y + height + padding,
                color
            )
        }
    }
}