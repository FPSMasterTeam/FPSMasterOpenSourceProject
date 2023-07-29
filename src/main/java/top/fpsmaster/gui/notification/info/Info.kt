package top.fpsmaster.gui.notification.info

import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import top.fpsmaster.FPSMaster
import top.fpsmaster.gui.notification.Notification
import top.fpsmaster.utils.render.RenderUtil
import java.awt.Color

class Info(text: String, type: Type) : Notification(text, type) {

    override fun onRender() {
        if (timerUtil.delay(1500F)) {
            timer = true
            timerUtil.reset()
        }
        var i = 0
        for (notification in FPSMaster.INSTANCE.notificationsManager!!.notifications) {
            if (notification === this) {
                break
            }
            if (notification is Info)
                i++
        }
        y = yAnimationUtils.animate((i.toFloat() * (height + 10)).toFloat(), y, 0.2f, true)
        val sr = ScaledResolution(FPSMaster.INSTANCE.mc)
        val client18 = FPSMaster.INSTANCE.fontLoader!!.client18

        height = 40.0
        width = 60.0 + client18.getStringWidth(text)

        RenderUtil.drawRoundRect10(
            (sr.scaledWidth + x / 10f - width).toFloat() - 10,
            10 + y,
            width.toFloat(),
            height.toFloat(),
            Color(
                0,
                0,
                0,
                (((width - x) / width).toFloat().coerceAtMost(1F).coerceAtLeast(0F) * 180).toInt()
            )
        )

        RenderUtil.drawImage(
            ResourceLocation("client/guis/notification/" + type.name + ".png"),
            (sr.scaledWidth + x / 10f - width).toFloat() + 5,
            (height / 2 + 4 + y).toFloat(),
            12f,
            12f,
            Color(
                255,
                255,
                255,
                (((width - x) / width).toFloat().coerceAtMost(1F).coerceAtLeast(0F) * 255).toInt()
            )
        )

        client18.drawString(
            text,
            (sr.scaledWidth + x / 10f - width).toFloat() + 20,
            (10 + y + height / 2).toFloat() - client18.FONT_HEIGHT / 2,
            Color(
                255,
                255,
                255,
                (((width - x) / width).toFloat().coerceAtMost(1F).coerceAtLeast(0F) * 255).toInt()
            ).rgb
        )
    }

}