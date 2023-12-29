package com.example.base_app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.VideoView

class ZoomableVideoView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null
) : VideoView(context, attrs) {
    private var scaleFactor = 0f
    private var scaleGestureDetector: ScaleGestureDetector
    init {
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor = detector.scaleFactor
            scaleVideo(scaleFactor)
            return true
        }
        private fun scaleVideo(scaleFactor: Float) {
            val layoutParams = layoutParams
            layoutParams.width = (width * scaleFactor).toInt()
            layoutParams.height = (height * scaleFactor).toInt()
            setLayoutParams(layoutParams)
        }
    }
}