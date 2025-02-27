package com.example.wpmod.model

data class RenderParams(
    val speed: Float,
    val scale: Float,
    val intensity: Float,
    val distortion: Float,
    val xOffset: Float,
    val yOffset: Float,
    val dotSize: Int,
    val xMax: Int,
    val yMax: Int,
    val step: Int,
    val xDivisor: Float,
    val xSubtractor: Float,
    val yDivisor: Float,
    val ySubtractor: Float,
    val oBase: Float,
    val oDivisor: Float,
    val sinDivisor: Float,
    val cosMultiplier: Float,
    val xKMultiplier: Float,
    val xScale: Float,
    val koMultiplier: Float,
    val yDivFactor: Float,
    val yScale: Float,
    val eoMultiplier: Float,
    // Include all parameters from JSON...
) {
    companion object {
        fun defaultVortexParams() = RenderParams(
            speed = 1f,
            scale = 1f,
            intensity = 1f,
            distortion = 5f,
            xOffset = 130f,
            yOffset = 70f,
            dotSize = 1,
            xMax = 200,
            yMax = 200,
            step = 2,
            xDivisor = 10f,
            xSubtractor = 10f,
            yDivisor = 5.7f,
            ySubtractor = 20f,
            oBase = 5f,
            oDivisor = 3f,
            sinDivisor = 10f,
            cosMultiplier = 5f,
            xKMultiplier = 0f,
            xScale = 0.1f,
            koMultiplier = 5f,
            yDivFactor = 0.4f,
            yScale = 0.7f,
            eoMultiplier = 1f
        )
    }
}