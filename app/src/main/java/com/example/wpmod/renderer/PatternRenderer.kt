package com.example.wpmod.renderer

import android.graphics.Canvas
import android.graphics.Paint
import com.example.wpmod.model.RenderPatternType
import com.example.wpmod.model.RenderParams
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

typealias RenderPattern = (Canvas, RenderParams, Float, Paint) -> Unit

class PatternRenderer {
    private var currentPattern: RenderPattern = ::renderVortexPattern
    private var params = RenderParams.defaultVortexParams()
    private val paint = Paint().apply { color = 0xFFFFFFFF.toInt() }

    fun loadPattern(patternType: RenderPatternType) {
        currentPattern = when (patternType) {
            RenderPatternType.VORTEX -> ::renderVortexPattern
            RenderPatternType.SPIRAL -> ::renderSpiralPattern
        }
    }

    fun render(canvas: Canvas, time: Float) {
        currentPattern(canvas, params, time, paint)
    }

    private fun renderVortexPattern(
        canvas: Canvas,
        p: RenderParams,
        time: Float,
        paint: Paint
    ) {
        for (y in 0..p.yMax step p.step) {
            for (x in 0..p.xMax step p.step) {
                val k = (x / p.xDivisor - p.xSubtractor) * p.scale
                val e = (y / p.yDivisor - p.ySubtractor) * p.scale
                val mag = sqrt(k * k + e * e)
                val o = p.oBase - mag / p.oDivisor
                val d = -p.distortion *
                        abs(sin(k / p.sinDivisor) * cos(e * p.cosMultiplier)) * p.intensity

                val px = ((x - d * k * p.xKMultiplier + d * k * sin(d + time)) *
                        p.xScale + k * o * p.koMultiplier + p.xOffset) * canvas.width / 400
                val py = ((y - (d * y)/p.yDivFactor + d * e * cos(d + time + o) *
                        sin(time + d)) * p.yScale + e * o * p.eoMultiplier + p.yOffset) * canvas.height / 400

                canvas.drawRect(px, py, px + p.dotSize, py + p.dotSize, paint)
            }
        }
    }

    private fun renderSpiralPattern(
        canvas: Canvas,
        p: RenderParams,
        time: Float,
        paint: Paint
    ) {
        for (y in 0..p.yMax step p.step) {
            for (x in 0..p.xMax step p.step) {
                val angle = (x + y) * p.scale + time * p.speed
                val radius = p.intensity * angle
                val px = (radius * cos(angle) + p.xOffset) * canvas.width / 400
                val py = (radius * sin(angle) + p.yOffset) * canvas.height / 400

                canvas.drawRect(px, py, px + p.dotSize, py + p.dotSize, paint)
            }
        }
    }
}