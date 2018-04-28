package com.iminput.listener

import com.iminput.widget.voice.VoicePressEnum

/**
 * Created by sunset on 2018/4/25.
 */

interface IPressStatusListener{
    fun onStatus(status: VoicePressEnum)
}

