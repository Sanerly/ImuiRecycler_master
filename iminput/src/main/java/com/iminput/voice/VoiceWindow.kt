package com.iminput.widget.voice

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.iminput.listener.InputListener


abstract class VoiceWindow : RelativeLayout {

    private var isStartTiming: Boolean = false
    protected lateinit var mListener: InputListener

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        visibility = View.GONE
    }


    fun onStatusObserver(listener: InputListener, status: VoicePressEnum) {
        this.mListener = listener
        when (status) {
            VoicePressEnum.VOICE_PRESS_SPEAK -> {
                visibility = View.GONE
                if (isStartTiming) endTiming() else cancelTiming()
                isStartTiming = false
            }
            VoicePressEnum.VOICE_RELEASE_SEND -> {
                visibility = View.VISIBLE
                if (!isStartTiming) {
                    startTiming()
                    isStartTiming = true
                }
            }
            VoicePressEnum.VOICE_RELEASE_CANCEL -> {
                isStartTiming = false
            }
        }
        onStatus(status)
    }

    /**
     * 开始录制
     */
    abstract fun startTiming()

    /**
     * 取消录制
     */
    abstract fun cancelTiming()

    /**
     * 结束录制
     */
    abstract fun endTiming()

    /**
     * 用户手指按下的几种状态
     */
    abstract fun onStatus(status: VoicePressEnum)
}