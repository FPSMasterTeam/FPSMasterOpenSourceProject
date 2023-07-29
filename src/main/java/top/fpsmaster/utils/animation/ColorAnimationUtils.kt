package top.fpsmaster.utils.math

import top.fpsmaster.modules.settings.ClientSettings
import java.awt.Color
import kotlin.math.abs

class ColorAnimationUtils {
    private val timerUtil = TimerUtil()
    fun animate(target: Color, current: Color, speed: Double, force: Boolean): Color {
        return animate(target, current, speed.toFloat(), force)
    }

    fun animate(target: Color, current: Color, speed: Double): Color {
        return animate(target, current, speed.toFloat(), false)
    }

    var red: Float = 0f
    var green: Float = 0f
    var blue: Float = 0f
    var alpha: Float = 0f

    @JvmOverloads
    fun animate(target: Color, current: Color, speed: Float, force: Boolean = false): Color {
        if (timerUtil.delay(16f)) { // 60FPS
            red = animate(target.red.toFloat(), current.red.toFloat(), speed, force)
            green = animate(target.green.toFloat(), current.green.toFloat(), speed, force)
            blue = animate(target.blue.toFloat(), current.blue.toFloat(), speed, force)
            alpha = animate(target.alpha.toFloat(), current.alpha.toFloat(), speed, force)
            timerUtil.reset()
        }
        return Color(
            red.toInt(),
            green.toInt(),
            blue.toInt(),
            alpha.toInt()
        )
    }

    fun animate(target: Float, current: Float, speed: Float, force: Boolean): Float {
        var current = current
        var speed = speed
        if (!ClientSettings.screenAnimation.value && !force) {
            return target
        }
        val larger: Boolean
        larger = target > current
        val bl = larger
        if (speed < 0.0f) {
            speed = 0.0f
        } else if (speed > 1.0) {
            speed = 1.0f
        }
        val dif = Math.max(target, current) - Math.min(target, current)
        var factor = dif * speed
        if (factor < 0.1f) {
            factor = 0.1f
        }
        current = if (larger) factor.let { current += it; current } else factor.let { current -= it; current }

        val res = if (abs(current - target) < 0.2) {
            target
        } else {
            current
        }
        return 255f.coerceAtMost(0f.coerceAtLeast(res))
    }

    companion object {
        private const val defaultSpeed = 0.125f
    }
}