package com.iminput.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import com.iminput.util.LogUtil

/**
 * 按住说话
 */
class VoiceButton : TextView {
    /**
     * 按下时的Y轴距离
     */
    private var downY: Float = 0.0f
    /**
     * 手指移动时当前的距离
     */
    private var currentY: Float = 0.0f

    /**
     * 手指从按下的位置到当前手指的位置的距离
     */
    private var mDistance: Float = 0.0f

    private lateinit var listener: onStatusListener

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                downY = event.y
                text = "松开 发送"
                listener.onStatus(VoicePressEnum.VOICE_RELEASE_SEND)
            }
            event.action == MotionEvent.ACTION_UP -> {
                text = "按住 说话"
                listener.onStatus(VoicePressEnum.VOICE_PRESS_SPEAK)
            }
            event.action == MotionEvent.ACTION_MOVE -> {
                currentY = event.y
                mDistance = downY - currentY
                text = when {
                    mDistance > 300 -> {
                        listener.onStatus(VoicePressEnum.VOICE_RELEASE_CANCEL)
                        "松开手指，取消发送"
                    }
                    else -> {
                        listener.onStatus(VoicePressEnum.VOICE_RELEASE_SEND)
                        "松开 发送"
                    }
                }
            }
        }
        return true
    }


    fun setStatusListener(listener: onStatusListener) {
        this.listener = listener
    }

    interface onStatusListener {
        fun onStatus(status: VoicePressEnum)
    }
}