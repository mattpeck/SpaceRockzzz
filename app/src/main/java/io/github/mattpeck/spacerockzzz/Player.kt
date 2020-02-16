package io.github.mattpeck.spacerockzzz

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Player(context: Context, var x: Float = 0f, var y: Float = 0f) {

    val bitmap: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.player)}

    var angle: Float = 0f
    var speed: Float = 0f

    fun update() {

    }

}