package com.example.wpmod.livewallpaper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import android.os.Handler
import android.os.Looper
import com.example.wpmod.model.RenderPatternType
import com.example.wpmod.renderer.PatternRenderer

class MathWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine = MathWallpaperEngine()

    inner class MathWallpaperEngine : WallpaperService.Engine() {
        private val renderer = PatternRenderer()
        private var isVisible = false
        private var time = 0f

        private val handler = Handler(Looper.getMainLooper())
        private val updateCallback = object : Runnable {
            override fun run() {
                drawFrame()
                handler.postDelayed(this, 16) // ~60 FPS
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            // Initial pattern load can be removed or kept for default behavior
        }

        override fun onVisibilityChanged(visible: Boolean) {
            isVisible = visible
            if (visible) {
                val sharedPref = applicationContext.getSharedPreferences("wallpaper_prefs", Context.MODE_PRIVATE)
                val patternName = sharedPref.getString("selected_pattern", RenderPatternType.VORTEX.name)
                val patternType = RenderPatternType.fromString(patternName ?: RenderPatternType.VORTEX.name)
                    ?: RenderPatternType.VORTEX
                renderer.loadPattern(patternType)
                handler.post(updateCallback)
            } else {
                handler.removeCallbacks(updateCallback)
            }
        }

        private fun drawFrame() {
            if (!isVisible) return

            var canvas: Canvas? = null
            try {
                canvas = surfaceHolder.lockCanvas()
                canvas?.let {
                    it.drawColor(Color.BLACK)
                    time += 0.1f
                    renderer.render(it, time)
                }
            } finally {
                canvas?.let { surfaceHolder.unlockCanvasAndPost(it) }
            }
        }

        fun updatePattern(patternType: RenderPatternType) {
            renderer.loadPattern(patternType)
        }
    }
}