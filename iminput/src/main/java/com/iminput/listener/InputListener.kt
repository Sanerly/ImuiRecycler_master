package com.iminput.listener

/**
 * Created by sunset on 2018/3/27.
 */
interface InputListener {
    /**
     * 监听软键盘弹出/关闭接口
     */
    fun onSoftKeyboardStatus(status: Boolean)

    /**
     * 发送文本消息监听
     */
    fun onTextSend(text: String)
    /**
     * 发送语音消息
     */
    fun onVoiceSend()
}