package com.saner.imuirecycler.input

import android.content.Context
import android.graphics.Color
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import com.iminput.util.LogUtil
import com.iminput.widget.VoiceWindow
import com.iminput.widget.VoicePressEnum
import com.saner.imuirecycler.R
import kotlinx.android.synthetic.main.layout_input_voice.view.*


/**
 * Created by sunset on 2018/4/26.
 */
class VoiceStatusLayout : VoiceWindow {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.layout_input_voice, this)
    }

    override fun onStatus(status: VoicePressEnum) {
        when (status) {
            VoicePressEnum.VOICE_PRESS_SPEAK -> {
                onSlip()
            }
            VoicePressEnum.VOICE_RELEASE_SEND -> {
                onSlip()
            }
            VoicePressEnum.VOICE_RELEASE_CANCEL -> {
                onRelease()
            }
        }
    }

    override fun cancelTiming() {
        LogUtil.logd("取消")
        mVoiceTime.stop()
        mVoiceTime.base = SystemClock.elapsedRealtime()
    }

    override fun startTiming() {
        LogUtil.logd("开始")
        //设置开始计时时间
        mVoiceTime.base = SystemClock.elapsedRealtime()
        //启动计时器
        mVoiceTime.start()
    }

    override fun endTiming() {
        mVoiceTime.stop()
        mVoiceTime.base = SystemClock.elapsedRealtime()
        LogUtil.logd("结束")
        mListener.onVoiceSend()
    }

    private fun onRelease() {
        mVoiceLabel.text = "松开手指，取消发送"
        mVoiceLabel.setBackgroundResource(R.drawable.voice_cancel_background)
        mVoiceImage.setImageResource(R.drawable.ic_chat_voice_cancel)
        mVoiceLabel.setTextColor(Color.WHITE)
    }


    private fun onSlip() {
        mVoiceImage.setImageResource(R.drawable.ic_chat_voice_speaking)
        mVoiceLabel.text = "手指上滑，取消发送"
        mVoiceLabel.setBackgroundColor(Color.WHITE)
        mVoiceLabel.setTextColor(Color.BLACK)
    }
}