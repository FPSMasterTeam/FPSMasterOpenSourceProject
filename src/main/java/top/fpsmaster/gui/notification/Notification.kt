package top.fpsmaster.gui.notification

import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import top.fpsmaster.FPSMaster
import top.fpsmaster.gui.notification.info.Info
import top.fpsmaster.utils.math.AnimationUtils
import top.fpsmaster.utils.math.TimerUtil
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

open class Notification(var text: String, var type: Type) {
    var timer: Boolean = false
    var width: Double = 150.0
    var height = 25.0
    var x: Float = 150f
    var y = 0f
    var position = 0f
    var `in` = true
    var animationUtils = AnimationUtils()
    var yAnimationUtils = AnimationUtils()
    var timerUtil = TimerUtil()


    open fun onRender() {
        if (timerUtil.delay(1000F)) {
            timer = true
            timerUtil.reset()
        }
        var i = 0
        for (notification in FPSMaster.INSTANCE.notificationsManager!!.notifications) {
            if (notification === this) {
                break
            }
            if (notification !is Info)
                i++
        }
        val color = Color(type.color)
        y = yAnimationUtils.animate((i.toFloat() * (height + 5)).toFloat(), y, 0.2f, true)
        val sr = ScaledResolution(FPSMaster.INSTANCE.mc)
        RenderUtil.drawImage(
            ResourceLocation("client/guis/notification/bubble.png"),
            (sr.scaledWidth + x - width).toFloat(),
            sr.scaledHeight - 50f - y - 20,
            290 / 2f,
            54 / 2f,
            Color(
                color.red,
                color.green,
                color.blue,
                255.coerceAtLeast(0.coerceAtMost(((150F - x) / width * 255).toInt()))
            )
        )

        RenderUtil.drawImage(
            ResourceLocation("client/guis/notification/" + type.name + ".png"),
            (sr.scaledWidth + x - width).toFloat() + 10,
            sr.scaledHeight - 50f - y - 12,
            12f,
            12f,
            Color(
                255,
                255,
                255,
                255.coerceAtLeast(0.coerceAtMost(((150F - x) / width * 255).toInt()))
            )
        )
        FPSMaster.INSTANCE.fontLoader!!.client18.drawString(
            text,
            (sr.scaledWidth + x - width + 30).toFloat(),
            sr.scaledHeight - 50f - y - 10,
            Color(
                255,
                255,
                255,
                255.coerceAtLeast(0.coerceAtMost(((150F - x) / width * 255).toInt()))
            ).rgb
        )
    }

    enum class Type(color: Color) {
        Success(FPSMaster.INSTANCE.theme.secondary),
        Error(FPSMaster.INSTANCE.theme.error),
        Info(FPSMaster.INSTANCE.theme.primary),
        Warning(FPSMaster.INSTANCE.theme.warning);

        var color: Int

        init {
            this.color = color.rgb
        }
    }
}