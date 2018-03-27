package com.iminput.listener

/**
 * Created by sunset on 2018/3/27.
 */
interface InputListener {
    /**
     * 监听软键盘弹出/关闭接口
     */
    fun onSoftKeyboardStatus(status: Boolean)
}