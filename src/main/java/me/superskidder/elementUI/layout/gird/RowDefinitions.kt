package me.superskidder.elementUI.layout.gird

import me.superskidder.elementUI.layout.Alignment
import me.superskidder.elementUI.layout.FlowLayout

class RowDefinitions : FlowLayout {
    var height2: String? = null

    constructor(height: String?, gutter: Float, animation: Boolean) : super(
        0f,
        0f,
        0f,
        0f,
        Alignment.Horizontal,
        gutter,
        animation
    ) {
        expand = false
        height2 = height
    }

    constructor(height: Int, gutter: Float, animation: Boolean) : super(
        0f,
        0f,
        0f,
        height.toFloat(),
        Alignment.Horizontal,
        gutter,
        animation
    ) {
        expand = false
    }

    constructor(height: Int, gutter: Float, animation: Boolean, weightLimit: Float, heightLimit: Float) : super(
        0f,
        0f,
        0f,
        height.toFloat(),
        Alignment.Horizontal,
        gutter,
        animation,
        weightLimit,
        heightLimit
    ) {
        expand = false
    }

    constructor(height: Float, animation: Boolean) : super(Alignment.Horizontal, 0f, animation) {
        this.height = height
        expand = false
    }

    constructor(height: Float) : super(Alignment.Horizontal, 0f, false) {
        this.height = height
        expand = false
    }

    constructor() : super(Alignment.Horizontal, 0f, false) {
        expand = false
    }

    override fun render(x: Float, y: Float) {
        super.render(x, y)
        width = (parent!!.width)
        if (height2 != null) {
            height = (parent?.surplusHeight!! * height2!!.toFloat() / 100)
        } else if (height == 0f) {
            height = (parent?.surplusHeight!!)
        }
    }
}