package top.fpsmaster.data

import java.awt.Color

class Theme {
    var primary = Color(0, 132, 255)
    var warning = Color(255, 205, 100)
    var error = Color(255, 40, 50)

    var secondary = Color(89, 255, 180)
    var tertiary = Color(255, 255, 255)
    var offline = Color(29, 29, 31)
    var nearlyWhite = Color(249, 249, 249)
    var grey = Color(182, 182, 182)
    var quaternary = Color(0, 0, 0)

    //Language Gui
    var language_sel = Color(237, 237, 237)
    var language_bg = Color(240, 240, 240)
    var language_text_sel = Color(255, 255, 255)
    var language_text_unsel = Color(0, 0, 0)

    fun setBrightness(color: Color,brightness:Float):Color{
        val hsb = Color.RGBtoHSB(color.red, color.green, color.blue, null)
        return Color.getHSBColor(hsb[0], hsb[1], brightness)
    }

    fun setSaturation(color: Color,saturation:Float):Color{
        val hsb = Color.RGBtoHSB(color.red, color.green, color.blue, null)
        return Color.getHSBColor(hsb[0], saturation, hsb[2])
    }

    fun getColor(color: Color,saturation: Float,brightness: Float): Color {
        return setSaturation(setBrightness(color,brightness),saturation)
    }

    companion object {
        fun setBrightness(color: Color,brightness:Float):Color{
            val hsb = Color.RGBtoHSB(color.red, color.green, color.blue, null)
            return Color.getHSBColor(hsb[0], hsb[1], brightness)
        }

        fun setSaturation(color: Color,saturation:Float):Color{
            val hsb = Color.RGBtoHSB(color.red, color.green, color.blue, null)
            return Color.getHSBColor(hsb[0], saturation, hsb[2])
        }
        fun getColor(color: Color,saturation: Float,brightness: Float): Color {
            return setSaturation(setBrightness(color,brightness),saturation)
        }

    }

}