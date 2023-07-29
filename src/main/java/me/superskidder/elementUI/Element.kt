package me.superskidder.elementUI

import me.superskidder.elementUI.impl.Component

interface Element {
    fun addElement(element: Component): Element
    fun render(x: Float, y: Float)
    fun mouseUpdate(x: Float, y: Float, mouseX: Float, mouseY: Float)
    fun mouseRelease(x: Float, y: Float, mouseX: Float, mouseY: Float)
    fun mouseClicked(x: Float, y: Float, btn: Int)
    fun display(x: Float, y: Float, mouseX: Float, mouseY: Float)
    fun update(x: Float, y: Float, mouseX: Float, mouseY: Float)
    fun keyTyped(char: Char, int: Int)

    fun mouseScrolled(dWheel: Int)
}