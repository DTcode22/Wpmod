package com.example.wpmod.renderer

import android.graphics.Canvas
import android.graphics.Paint
import com.example.wpmod.model.RenderPatternType
import com.example.wpmod.model.RenderParams
import kotlin.math.*

typealias RenderPattern = (Canvas, RenderParams, Float, Paint) -> Unit

class PatternRenderer {
    private var currentPattern: RenderPattern = ::renderVortexPattern
    private var params: RenderParams = RenderParams.defaultVortexParams()
    private val paint = Paint().apply { color = 0xFFFFFFFF.toInt() } // White paint

    /**
     * Loads the specified pattern type, setting both the rendering function and parameters.
     */
    fun loadPattern(patternType: RenderPatternType) {
        currentPattern = when (patternType) {
            RenderPatternType.VORTEX -> ::renderVortexPattern
            RenderPatternType.SPIRAL -> ::renderSpiralPattern
        }
        params = when (patternType) {
            RenderPatternType.VORTEX -> RenderParams.defaultVortexParams()
            RenderPatternType.SPIRAL -> RenderParams.defaultSpiralParams()
        }
    }

    /**
     * Renders the current pattern on the canvas using the given time.
     */
    fun render(canvas: Canvas, time: Float) {
        currentPattern(canvas, params, time, paint)
    }

    /**
     * Renders the vortex pattern (existing implementation).
     */
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
                val py = ((y - (d * y) / p.yDivFactor + d * e * cos(d + time + o) *
                        sin(time + d)) * p.yScale + e * o * p.eoMultiplier + p.yOffset) * canvas.height / 400

                canvas.drawRect(
                    px.toFloat(),
                    py.toFloat(),
                    (px + p.dotSize).toFloat(),
                    (py + p.dotSize).toFloat(),
                    paint
                )
            }
        }
    }

    /**
     * Renders the spiral pattern based on the JavaScript implementation.
     */
    private fun renderSpiralPattern(
        canvas: Canvas,
        p: RenderParams,
        time: Float,
        paint: Paint
    ) {
        for (y in 0..p.yMax step p.step) {
            for (x in 0..p.xMax step p.step) {
                val k = x / p.xDivisor - p.xSubtractor
                val e = y / p.yDivisor + p.ySubtractor

                // Skip to avoid division by zero in tan(1 / k)
                if (abs(k) < 0.001) continue

                val mag = sqrt(k * k + e * e)
                val o = mag / p.oDivisor
                val c = (o * e) / p.yDivFactor - time / 8

                val q = x + 99 + tan(1 / k) +
                        o * k * (cos(e * p.cosMultiplier) / 2 +
                                 cos(y / p.yDivisor) / 0.7) *
                        sin(o * p.koMultiplier - time * 2)

                val px = q * p.xScale * sin(c) + p.xOffset
                val py = p.yOffset + y * cos(c * p.eoMultiplier - o) - (q / 2) * cos(c)

                val canvasPx = (px * canvas.width) / 400
                val canvasPy = (py * canvas.height) / 400

                canvas.drawRect(
                    canvasPx.toFloat(),
                    canvasPy.toFloat(),
                    (canvasPx + p.dotSize).toFloat(),
                    (canvasPy + p.dotSize).toFloat(),
                    paint
                )
            }
        }
    }
}