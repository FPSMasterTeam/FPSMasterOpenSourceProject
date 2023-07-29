package me.superskidder.elementUI.layout

import me.superskidder.elementUI.impl.Component

open class BasicLayout : Component() {
    override fun render(x: Float, y: Float) {
        super.render(x, y)
    }

    override fun display(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.display(x, y, mouseX, mouseY)
        for (element in elements) {
            element.render(x, y)
            if (element.x + element.width > width) {
                width = element.x + element.width
            }
            if (element.y + element.height > height) {
                height = element.y + element.height
            }
        }
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
    }
}