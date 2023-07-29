package top.fpsmaster.gui.element

import me.superskidder.elementUI.layout.GridLayout

class PanelComponent : GridLayout {
    constructor(width: Float, height: Float) : super(width, height) {
        animation = true
    }

    constructor(width: Float, height: Float, animation: Boolean) : super(width, height) {
        this.animation = animation
    }
}