package io.github.mattpeck.spacerockzzz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
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

    private val screenWidth = this.resources.displayMetrics.widthPixels
    private val screenHeight = this.resources.displayMetrics.heightPixels
    private val paint: Paint = Paint()
    private val surfaceHolder: SurfaceHolder = holder

    private val player: Player = Player(context,screenWidth / 2f, screenHeight / 2f)

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
            drawFPSText(canvas)
            drawPlayer(canvas)
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_UP -> {
                    player.rotationState = Player.RotationStates.NONE
                }
                MotionEvent.ACTION_DOWN -> {
                    if ( it.x <= screenWidth / 2) {
                        player.rotationState = Player.RotationStates.COUNTER_CLOCKWISE
                    }
                    else {
                        player.rotationState = Player.RotationStates.CLOCKWISE
                    }
                }
                else -> {
                    // Empty
                }
            }
        }

        return true
    }

    // Drawing Helpers

    private fun drawFPSText(canvas: Canvas) {
        paint.color = Color.WHITE
        paint.textSize = 45f
        canvas.drawText("FPS: $currentFPS", 20f, 40f, paint)
    }

    private fun drawPlayer(canvas: Canvas) {
        val matrix = Matrix().apply {
            setRotate(player.angle, player.bitmap.width / 2f, player.bitmap.height / 2f)
            postTranslate(player.x, player.y)
        }
        canvas.drawBitmap(player.bitmap, matrix, paint)
    }
}