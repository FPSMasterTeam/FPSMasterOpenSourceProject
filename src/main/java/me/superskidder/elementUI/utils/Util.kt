package me.superskidder.elementUI.utils

import net.minecraft.client.Minecraft

class Util {
    var mc: Minecraft? = null
    fun init() {
        mc = Minecraft.getMinecraft()
    }
}