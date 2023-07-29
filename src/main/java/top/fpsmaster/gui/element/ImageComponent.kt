package top.fpsmaster.gui.element

import me.superskidder.elementUI.impl.Component
import net.minecraft.util.ResourceLocation
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

open class ImageComponent(x: Int, y: Int, width: Int, height: Int, var image: String, var color: Color) :
    Component(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat()) {

    override fun render(x: Float, y: Float) {
        super.render(x, y)
        RenderUtil.drawImage(ResourceLocation(image), x, y, width, height, color)
    }
}