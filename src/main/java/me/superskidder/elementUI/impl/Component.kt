package me.superskidder.elementUI.impl

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import me.superskidder.elementUI.Element
import top.fpsmaster.FPSMaster
import top.fpsmaster.modules.settings.ClientSettings
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

@AllArgsConstructor
@NoArgsConstructor
@Data
open class Component : Element {
    var id: String = ""

    @JvmField
    var x: Float = 0f

    @JvmField
    var y: Float = 0f
    var elements = ArrayList<Component>()

    // parent
    var parent: Component? = null
    var dX = 0f //实际位置
    var dY = 0f //实际位置

    @JvmField
    var width: Float = 0f

    @JvmField
    var height: Float = 0f

    @JvmField
    var fWidth = 0f

    @JvmField
    var fHeight = 0f
    var visible = true
    var enabled = true
    var hovered = false

    @JvmField
    var useScissor = false
    var widthLimit = 0f
    var heightLimit = 0f
    var xLimit = 0f
    var yLimit = 0f

    @JvmField
    var animation = false
    var surplusWidth = 0f
    var surplusHeight = 0f
    private val widthAnim = AnimationUtils()
    private val heightAnim = AnimationUtils()

    @JvmField
    var theme = FPSMaster.INSTANCE.theme
    var hoverAppend: Float = 0f


    constructor(x: Float, y: Float, width: Float, height: Float) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        this.theme = FPSMaster.INSTANCE.theme
    }

    constructor(x: Float, y: Float, width: Float, height: Float, parent: Component?) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        this.parent = parent
        this.theme = FPSMaster.INSTANCE.theme

    }

    constructor()

    override fun addElement(element: Component): Component {
        element.parent = this
        elements.add(element)
        return this
    }

    fun addElements(vararg element: Component): Component {
        for (component in element) {
            component.parent = this
            elements.add(component)
        }
        return this
    }

    //remove element
    fun removeElement(element: Component) {
        elements.remove(element)
    }

    override fun render(x: Float, y: Float) {}
    fun out(): Boolean {
        return (xLimit != 0f && x < xLimit) || (yLimit != 0f && y < yLimit) || (widthLimit != 0f && x > this.x + xLimit + widthLimit) || (heightLimit != 0f && y > this.y + yLimit + heightLimit)
    }

    override fun mouseUpdate(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        if ((xLimit != 0f && x < xLimit) || (yLimit != 0f && y < yLimit) || (widthLimit != 0f && x > this.x + xLimit + widthLimit) || (heightLimit != 0f && y > this.y + yLimit + heightLimit)) {
            return
        }
        if (visible) {
            for (element in elements) {
                element.mouseUpdate(x, y, mouseX, mouseY)
            }
        }
        mouseRelease(x, y, mouseX, mouseY)
    }

    override fun mouseRelease(x: Float, y: Float, mouseX: Float, mouseY: Float) {}
    override fun mouseClicked(x: Float, y: Float, btn: Int) {
        if ((xLimit != 0f && x < xLimit) || (yLimit != 0f && y < yLimit) || (widthLimit != 0f && x > this.x + xLimit + widthLimit) || (heightLimit != 0f && y > this.y + yLimit + heightLimit)) {
            return
        }
        for (element in elements) {
            if (element.hovered) {
                element.mouseClicked(x, y, btn)
            }
        }
    }

    override fun display(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        // debug
        dX = this.x + x
        dY = this.y + y
        if (ClientSettings.debug.value) {
            RenderUtil.drawBordered(
                (x + this.x).toDouble(),
                (y + this.y).toDouble(),
                width.toDouble(),
                height.toDouble(),
                0.5,
                Color(0, 0, 0, 10).rgb,
                Color(255, 0, 0, 50).rgb
            )
        }
        update(x, y, mouseX, mouseY)
        render(this.x + x, this.y + y)
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        mouseUpdate(x, y, mouseX, mouseY)
        for (element in elements) {
            element.update(x, y, mouseX, mouseY)
        }
        if (animation) {
            if (fWidth != 0f) {
                width = widthAnim.animate(fWidth.toDouble(), width.toDouble(), 0.2, false).toFloat()
            }
            if (fHeight != 0f) {
                height = heightAnim.animate(fHeight.toDouble(), height.toDouble(), 0.2, false).toFloat()
            }
        } else {
            if (fWidth != 0f) {
                width = fWidth
            }
            if (fHeight != 0f) {
                height = fHeight
            }
        }
        hovered =
            mouseX >= dX - hoverAppend && mouseX <= dX + width + hoverAppend && mouseY >= dY - hoverAppend && mouseY <= dY + height + hoverAppend
    }

    override fun mouseScrolled(dWheel: Int) {
        for (element in elements) {
            element.mouseScrolled(dWheel)
        }
    }

    fun setId(name: String): Component {
        id = name
        return this
    }

    override fun keyTyped(char: Char, int: Int) {
        for (element in elements) {
            element.keyTyped(char, int)
        }
    }
}