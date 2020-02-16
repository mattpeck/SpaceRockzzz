package io.github.mattpeck.spacerockzzz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context) : SurfaceView(context), Runnable {

    object GameConstants {
        const val FRAMES_PER_SECOND: Int = 30
        const val SKIP_TICKS: Int = 1000 / FRAMES_PER_SECOND
    }

    @Volatile var isPlaying: Boolean = false

    private var thread: Thread? = null
    private var sleepTime: Long = 0
    private var nextGameTick: Long = System.currentTimeMillis()
    private var currentFPS: Long = 0

    private val paint: Paint = Paint()
    private val surfaceHolder: SurfaceHolder = holder

    private val player: Player = Player(context)

    override fun run() {
        while (isPlaying) {
            val startTime: Long = System.currentTimeMillis()

            // Game Loop
            update()
            draw()
            control()

            // Calculate FPS
            val totalTime = System.currentTimeMillis() - startTime
            if (totalTime > 0) {
                currentFPS = 1000 / totalTime
            }
        }
    }

    private fun update() {
        player.update()
    }

    private fun draw() {
        if (surfaceHolder.surface.isValid) {
            val canvas: Canvas = surfaceHolder.lockCanvas()
            // Black Background
            canvas.drawColor(Color.BLACK)
            // FPS Text
            paint.color = Color.WHITE
            paint.textSize = 45f
            canvas.drawText("FPS: $currentFPS", 20f, 40f, paint)
//            canvas.drawBitmap(player.bitmap, player.x, player.y, paint)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun control() {
        nextGameTick += GameConstants.SKIP_TICKS
        sleepTime = nextGameTick - System.currentTimeMillis()
        if (sleepTime >= 0) {
            Thread.sleep(sleepTime)
        }
    }

    fun pause() {
        isPlaying = false
        try {
            thread?.join()
        } catch (e:InterruptedException) {
            
        }
    }

    fun resume() {
        isPlaying = true
        thread = Thread(this@GameView)
        thread?.start()
    }
}