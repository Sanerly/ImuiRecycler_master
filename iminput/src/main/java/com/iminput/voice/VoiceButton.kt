package com.iminput.widget.voice

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import com.iminput.R
import com.iminput.listener.IPressStatusListener

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

    private lateinit var listener: IPressStatusListener

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        text = "按住 说话"
        setBackgroundResource(R.drawable.im_message_import)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when {
            event.action == MotionEvent.ACTION_DOWN -> {
                downY = event.y
                text = "松开 发送"
                setBackgroundResource(R.drawable.im_message_press_import)
                listener.onStatus(VoicePressEnum.VOICE_RELEASE_SEND)
            }
            event.action == MotionEvent.ACTION_UP -> {
                text = "按住 说话"
                setBackgroundResource(R.drawable.im_message_import)
                listener.onStatus(VoicePressEnum.VOICE_PRESS_SPEAK)
            }
            event.action == MotionEvent.ACTION_MOVE -> {
                currentY = event.y
                mDistance = downY - currentY
                setBackgroundResource(R.drawable.im_message_press_import)
                text = when {
                    mDistance > 450 -> {
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


    fun setStatusListener(listener: IPressStatusListener) {
        this.listener = listener
    }

}