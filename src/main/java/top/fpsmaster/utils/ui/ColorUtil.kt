package top.fpsmaster.utils.ui

import java.awt.Color

class ColorUtil {
    fun rgb2Hex(r: Int, g: Int, b: Int): String {
        return (String.format("#%02x%02x%02x", r, g, b))
    }

    fun hex2RGB(hex: String): Color {
        val rgb = IntArray(3)
        rgb[0] = Integer.valueOf(hex.substring(1, 3), 16)
        rgb[1] = Integer.valueOf(hex.substring(3, 5), 16)
        rgb[2] = Integer.valueOf(hex.substring(5, 7), 16)
        return Color(rgb[0], rgb[1], rgb[2])
    }
}