package com.pubg.sb.pubgassist.service

import android.accessibilityservice.AccessibilityService
import android.app.Instrumentation
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

/**
 *  @author XY
 *  @apiNote 监听键盘事件Service
 */
class KeyBoardService : AccessibilityService() {
    private val TAG = "PUBGSB"

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            Log.e(TAG, "onKeyEvent code = " + event.keyCode)
            performKeyPress(event)
        }
        return super.onKeyEvent(event)
    }

    private fun performKeyPress(event: KeyEvent?) {
        if (event?.keyCode == KeyEvent.KEYCODE_F1) {
            Thread {
                try {
                    Thread.sleep(50)
                    typeIn(KeyEvent.KEYCODE_6)
                    Thread.sleep(50)
                    typeIn(KeyEvent.KEYCODE_5)
                    Thread.sleep(50)
                    typeIn(KeyEvent.KEYCODE_4)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }.start()
        }
    }


    private fun typeIn(KeyCode: Int) {
        try {
            val inst = Instrumentation()
            inst.sendKeyDownUpSync(KeyCode)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}