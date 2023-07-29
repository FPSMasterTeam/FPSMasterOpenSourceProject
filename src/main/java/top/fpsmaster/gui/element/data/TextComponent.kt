package top.fpsmaster.gui.element.data

import me.superskidder.elementUI.layout.BasicLayout
import net.minecraft.world.biome.BiomeGenBase.Height
import org.lwjgl.input.Mouse
import top.fpsmaster.core.values.values.TextValue
import top.fpsmaster.utils.render.GuiInputField

class TextComponent : BasicLayout {
    var value: TextValue
    var box: GuiInputField

    constructor(v: TextValue, width: Float, height: Float) {
        this.value = v;
        box = GuiInputField(x, y, width, height)
        box.text = value.value
        this.width = width
        this.height = height
    }

    override fun display(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.display(x, y, mouseX, mouseY)

        box.x = x
        box.y = y
        box.draw(mouseX.toInt(), mouseY.toInt())
    }

    override fun mouseRelease(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.mouseRelease(x, y, mouseX, mouseY)
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
        box.update()
        if(Mouse.isButtonDown(0) && !hovered) {
            box.setSelected(false)
        }
        value.value = box.text
    }

    override fun keyTyped(char: Char, int: Int) {
        super.keyTyped(char, int)
        box.keyTyped(char, int)
    }

    override fun mouseClicked(x: Float, y: Float, btn: Int) {
        super.mouseClicked(x, y, btn)
        box.mouseClicked(x.toInt(), y.toInt(), btn)
    }

}