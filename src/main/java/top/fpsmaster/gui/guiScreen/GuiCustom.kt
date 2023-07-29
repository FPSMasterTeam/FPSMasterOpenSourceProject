package top.fpsmaster.gui.guiScreen

import top.fpsmaster.FPSMaster
import top.fpsmaster.gui.classicComponents.DragAble
import top.fpsmaster.gui.keystrokes.KeyStrokes
import top.fpsmaster.gui.keystrokes.render.KeystrokesRenderer
import java.io.IOException

class GuiCustom {
    @JvmField
    var keyStrokes = KeyStrokes()

    init {
        keyStrokes.renderer = KeystrokesRenderer(keyStrokes)
        dragAbles.add(DragAble(FPSMaster.INSTANCE.moduleManager!!.modules["Sprint"]))
        dragAbles.add(DragAble(FPSMaster.INSTANCE.moduleManager!!.modules["PotionDisplay"]))
        dragAbles.add(DragAble(FPSMaster.INSTANCE.moduleManager!!.modules["MemoryManager"]))
        dragAbles.add(DragAble(FPSMaster.INSTANCE.moduleManager!!.modules["KeyStrokes"]))
        dragAbles.add(DragAble(FPSMaster.INSTANCE.moduleManager!!.modules["Scoreboard"]))
        dragAbles.add(DragAble(FPSMaster.INSTANCE.moduleManager!!.modules["Coordinates"]))
        dragAbles.add(DragAble(FPSMaster.INSTANCE.moduleManager!!.modules["ArmorStatus"]))
    }

    fun drawScreen(mouseX: Int, mouseY: Int) {
        for (module in dragAbles) {
            if (module.mod.stage) {
                module.draw(mouseX.toFloat(), mouseY.toFloat())
            }
        }
    }

    fun mouseRelease(mouseX: Int, mouseY: Int) {
        for (module in dragAbles) {
            if (module.mod.stage) {
                module.mouse(mouseX, mouseY)
            }
        }
    }

    @Throws(IOException::class)
    fun mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int) {
        for (module in dragAbles) {
            module.clicked(mouseX, mouseY, mouseButton)
        }
    }

    fun drawGuis() {
        for (module in dragAbles) {
            module.draw2()
        }
    }

    companion object {
        var dragAbles = ArrayList<DragAble>()
    }
}