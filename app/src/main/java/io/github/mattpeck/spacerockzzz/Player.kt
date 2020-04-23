package io.github.mattpeck.spacerockzzz

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Player(context: Context, var x: Float = 0f, var y: Float = 0f) {

    enum class RotationStates {
        COUNTER_CLOCKWISE, CLOCKWISE, NONE
    }

    val bitmap: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.player) }

    var rotationState: RotationStates = RotationStates.NONE
    var angle: Float = 0f
    var speed: Float = 0f

    fun update() {
        when (rotationState) {
            RotationStates.CLOCKWISE -> {
                angle = (angle + 4) % 360
            }
            RotationStates.COUNTER_CLOCKWISE -> {
                angle = (angle - 4) % 360
            }
            else -> {
                // Empty
            }
        }
    }

}