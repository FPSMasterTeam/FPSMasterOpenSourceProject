package me.superskidder.elementUI.utils.animation

class AnimationValue() {

    var duration = 0
    var value:Float = 0f

    fun easeInQuad(startValue: Float, changeValue: Float) {
        value = (changeValue * Math.pow(
            (System.currentTimeMillis() - startValue).toDouble() / duration,
            2.0
        ) + startValue).toFloat()
    }

    fun easeOutQuad(startValue: Float, changeValue: Float) {
        value = (-changeValue * Math.pow(
            (System.currentTimeMillis() - startValue).toDouble() / duration - 1,
            2.0
        ) + startValue).toFloat()
    }

    fun easeInOutQuad(startValue: Float, changeValue: Float) {
        value = (if ((System.currentTimeMillis() - startValue) / duration < 0.5) 2 * changeValue * Math.pow(
            (System.currentTimeMillis() - startValue).toDouble() / duration,
            2.0
        ) + startValue else -2 * changeValue * Math.pow(
            (System.currentTimeMillis() - startValue).toDouble() / duration - 1,
            2.0
        ) + startValue).toFloat()
    }
}