package me.superskidder.elementUI.layout.gird

import me.superskidder.elementUI.layout.Alignment
import me.superskidder.elementUI.layout.FlowLayout

class ColumnDefinitions : FlowLayout {
    var width2: String? = null

    constructor(width: String?, gutter: Float, animation: Boolean) : super(
        0f,
        0f,
        0f,
        0f,
        Alignment.Vertical,
        gutter,
        animation
    ) {
        expand = false
        width2 = width
    }

    constructor(width: Int, gutter: Float, animation: Boolean) : super(
        0f,
        0f,
        width.toFloat(),
        0f,
        Alignment.Vertical,
        gutter,
        animation
    ) {
        expand = false
    }

    constructor(width: Int, gutter: Float, animation: Boolean, weightLimit: Float, heightLimit: Float) : super(
        0f,
        0f,
        width.toFloat(),
        0f,
        Alignment.Vertical,
        gutter,
        animation,
        weightLimit,
        heightLimit
    ) {
        expand = false
    }

    constructor(width: Float, animation: Boolean) : super(Alignment.Vertical, 0f, animation) {
        this.width = width
        expand = false
    }

    constructor(width: Float) : super(Alignment.Vertical, 0f, false) {
        this.width = width
        expand = false
    }

    constructor() : super(Alignment.Vertical, 0f, false) {
        expand = false
    }

    override fun render(x: Float, y: Float) {
        super.render(x, y)
        height = parent!!.height
        if (width2 != null) {
            width = (parent?.surplusWidth!! * width2!!.toFloat() / 100)
        } else if (width == 0f) {
            width = (parent?.surplusWidth!!)
        }
    }
}