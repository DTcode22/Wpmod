package com.example.wpmod.livewallpaper
import com.example.wpmod.renderer.PatternRenderer
import com.example.wpmod.model.RenderPatternType
import android.graphics.Canvas
import android.graphics.Color
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import android.os.Handler
import android.os.Looper

class MathWallpaperService : WallpaperService() {
    override fun onCreateEngine(): Engine = MathWallpaperEngine()

    inner class MathWallpaperEngine : WallpaperService.Engine() {
        private val renderer = PatternRenderer()
        private var isVisible = false
        private var time = 0f

        // Add handler initialization
        private val handler = Handler(Looper.getMainLooper())

        private val updateCallback = object : Runnable {
            override fun run() {
                drawFrame()
                handler.postDelayed(this, 16) // ~60 FPS
            }
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            renderer.loadPattern(RenderPatternType.VORTEX)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            isVisible = visible
            if (visible) {
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