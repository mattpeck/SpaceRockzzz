package io.github.mattpeck.spacerockzzz

import android.content.Context
import android.view.SurfaceView

class GameView(context:Context) : SurfaceView(context), Runnable {


    @Volatile var isPlaying:Boolean = false
    private var mThread:Thread? = null

    override fun run() {
        while (isPlaying) {
            update()
            draw()
            control()
        }
    }

    private fun update() {

    }

    private fun draw() {

    }

    private fun control() {

    }

    fun pause() {
        isPlaying = false
        try {
            mThread?.join()
        } catch (e:InterruptedException) {
            
        }
    }

    fun resume() {
        isPlaying = true
        mThread = Thread(this@GameView)
        mThread?.start()
    }
}