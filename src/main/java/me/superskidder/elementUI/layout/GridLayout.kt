package me.superskidder.elementUI.layout

open class GridLayout : FlowLayout {
    constructor(width: Float, height: Float, gutter: Float, animation: Boolean) : super(
        0f,
        0f,
        width,
        height,
        Alignment.Vertical,
        gutter,
        animation
    ) {
        expand = false
    }

    constructor(width: Float, height: Float, gutter: Float) : super(
        0f,
        0f,
        width,
        height,
        Alignment.Vertical,
        gutter,
        false
    ) {
        expand = false
    }

    constructor(width: Float, height: Float) : super(0f, 0f, width, height, Alignment.Vertical, 0f, false) {
        expand = false
    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
    }
}