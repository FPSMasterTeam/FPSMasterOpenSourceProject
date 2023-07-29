package me.superskidder.elementUI.layout

import me.superskidder.elementUI.impl.Component
import org.lwjgl.opengl.GL11
import top.fpsmaster.gui.element.LabelComponent
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.render.RenderUtil

open class FlowLayout : Component {
    private var alignment: Alignment
    var forceWrap = false // 是否强制使用Limit值换行（若未定义则使用父组件limit）
    private val widthAnim = AnimationUtils()
    private val heightAnim = AnimationUtils()
    private var gutter = 0f
    var expand = true //大小是否随子组件变化

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        alignment: Alignment,
        gutter: Float,
        animation: Boolean
    ) : super(x, y, width, height) {
        this.alignment = alignment
        this.gutter = gutter
        this.animation = animation
    }

    constructor(alignment: Alignment, gutter: Float, animation: Boolean) : super(0f, 0f, 0f, 0f) {
        this.alignment = alignment
        this.gutter = gutter
        this.animation = animation
    }

    constructor(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        alignment: Alignment,
        gutter: Float,
        animation: Boolean,
        weightLimit: Float,
        heightLimit: Float
    ) : super(x, y, width, height) {
        this.alignment = alignment
        this.gutter = gutter
        this.animation = animation
        widthLimit = weightLimit
        this.heightLimit = heightLimit
    }

    override fun render(x: Float, y: Float) {
        super.render(x, y)

    }

    override fun update(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        super.update(x, y, mouseX, mouseY)
        var x = x
        var y = y
        surplusWidth = width
        surplusHeight = height
        val x1 = x
        val y1 = y
        if (visible) {
            for (element in elements) {
                if (alignment == Alignment.Horizontal) {

                    if (forceWrap) {
                        if (x + element.width > (if (xLimit + widthLimit == 0f) parent!!.xLimit + parent!!.widthLimit else xLimit + widthLimit)) {
                            x = x1
                            y += element.height + 10
                        }
                    }
                    if (xLimit == 0f || yLimit == 0f || x1 + element.width + gutter < xLimit + widthLimit && x1 > xLimit) { //是否超出了限制
                        element.update(x, y, mouseX, mouseY)
                    }
                    x += element.width + gutter // X位置递增
                    if (forceWrap) {
                        if (x - x1 > fWidth) {
                            fWidth = x - x1
                        }
                    }
                    if (expand) if (height < element.height) { // 高度适应（让flow布局的高度适应子组件）
                        height = element.height
                    }
                    surplusWidth -= element.width + gutter //剩余宽度
                } else if (alignment == Alignment.Vertical) {
                    if (xLimit == 0f || yLimit == 0f || y <= yLimit + heightLimit && y + element.height + gutter >= yLimit) { //是否超出了限制
                        element.update(x, y, mouseX, mouseY)
                    } else if (element is LabelComponent) {
                        element.dX = x
                        element.dY = y
                    }
                    y += element.height + gutter // Y位置递增
                    //                    if (expand)
//                        if (width<x1-x) { // 宽度适应（让flow布局的宽度适应子组件）
//                            width = x1-x;
//                        }
                    surplusHeight -= element.height + gutter //剩余高度
                }
            }
            if (expand) {
                if (alignment == Alignment.Horizontal) {
                    x -= gutter
                } else if (alignment == Alignment.Vertical) {
                    y -= gutter
                }
                if (!forceWrap) fWidth = x - x1
                fHeight = y - y1
                if (forceWrap) {
                    fHeight += gutter
                }
            }
        }
    }

    override fun display(x: Float, y: Float, mouseX: Float, mouseY: Float) {
        var x = x
        var y = y
        super.display(x, y, mouseX, mouseY)
        if (useScissor) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST)
            RenderUtil.doGlScissor(
                xLimit.coerceAtLeast(x),
                yLimit.coerceAtLeast(y),
                widthLimit.coerceAtLeast(width),
                heightLimit.coerceAtLeast(height)
            )
        }
        surplusWidth = width
        surplusHeight = height
        val x1 = x
        val y1 = y
        if (visible) {
            for (element in elements) {
                if (alignment == Alignment.Horizontal) {
                    if (forceWrap) {
                        if (x + element.width > (if (xLimit + widthLimit == 0f) parent!!.xLimit + parent!!.widthLimit else xLimit + widthLimit)) {
                            x = x1
                            y += element.height + 10
                        }
                    }
                    //                    } else {
//                        if (x + element.width > x1 + width) {
//                            x = x1;
//                            y += element.height + 10;
//                        }
//                    }
                    if (xLimit == 0f || yLimit == 0f || x1 + element.width + gutter < xLimit + widthLimit && x1 > xLimit) { //是否超出了限制
                        element.display(x, y, mouseX, mouseY)
                    }
                    x += element.width + gutter // X位置递增
                    if (forceWrap) {
                        if (x - x1 > fWidth) {
                            fWidth = x - x1
                        }
                    }
                    if (expand) if (height < element.height) { // 高度适应（让flow布局的高度适应子组件）
                        height = element.height
                    }
                    surplusWidth -= element.width + gutter //剩余宽度
                } else if (alignment == Alignment.Vertical) {
                    if (xLimit == 0f || yLimit == 0f || y <= yLimit + heightLimit && y + element.height + gutter >= yLimit) { //是否超出了限制
                        element.display(x, y, mouseX, mouseY)
                    } else if (element is LabelComponent) {
                        element.dX = x
                        element.dY = y
                    }
                    y += element.height + gutter // Y位置递增
                    //                    if (expand)
//                        if (width<x1-x) { // 宽度适应（让flow布局的宽度适应子组件）
//                            width = x1-x;
//                        }
                    surplusHeight -= element.height + gutter //剩余高度
                }
            }
            if (expand) {
                if (alignment == Alignment.Horizontal) {
                    x -= gutter
                } else if (alignment == Alignment.Vertical) {
                    y -= gutter
                }
                if (!forceWrap) fWidth = x - x1
                fHeight = y - y1
                if (forceWrap) {
                    fHeight += gutter
                }
            }
        }
        if (useScissor) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST)
        }
    }

    fun setExpand(expand: Boolean): Component {
        this.expand = expand
        return this
    }
}