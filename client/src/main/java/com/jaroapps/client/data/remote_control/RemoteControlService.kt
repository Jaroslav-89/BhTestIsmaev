package com.jaroapps.client.data.remote_control

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import com.jaroapps.client.data.dto.SwipeDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class RemoteControlService() : AccessibilityService() {

    private val coordinateX = 500f
    private var coordinateY = 500f

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }

    fun performSwipe(swipe: SwipeDto, completion: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val path = Path().apply {
                if (swipe.swipeDirection.contains(UP)) {
                    moveTo(coordinateX, coordinateY + swipe.swipeValue.toFloat())
                    lineTo(coordinateX, coordinateY)
                } else {
                    moveTo(coordinateX, coordinateY)
                    lineTo(coordinateX, coordinateY + swipe.swipeValue.toFloat())
                }
            }

            val gestureBuilder = GestureDescription.Builder()
            gestureBuilder.addStroke(GestureDescription.StrokeDescription(path, 0, 500))
            val gesture = gestureBuilder.build()

            val callback = object : GestureResultCallback() {
                override fun onCompleted(gestureDescription: GestureDescription?) {
                    super.onCompleted(gestureDescription)
                    completion()
                }
            }

            try {
                dispatchGesture(gesture, callback, Handler(Looper.getMainLooper()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        var instance: RemoteControlService? = null
        const val UP = "up"
    }
}