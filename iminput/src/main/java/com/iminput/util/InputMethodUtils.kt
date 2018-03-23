package com.iminput.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.annotation.SuppressLint


/**
 * @author xh2009cn
 */
object InputMethodUtils {

    var isKeyboardShowing: Boolean = false

    private var sKeyBoardHeight = DisplayUtils.defaultKeyboardHeight

    /**
     * 监听软键盘弹出/关闭接口
     */
    interface OnKeyboardEventListener {
        /**
         * 软键盘弹出
         */
        fun onSoftKeyboardOpened()

        /**
         * 软键盘关闭
         */
        fun onSoftKeyboardClosed()
    }


    /**
     * 隐藏输入法
     *
     * @param currentFocusView 当前焦点view
     */
    fun hideKeyboard(currentFocusView: View?) {
        if (currentFocusView != null) {
            val token = currentFocusView.windowToken
            if (token != null) {
                val im = currentFocusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(token, 0)
            }
        }
    }

    /**
     * 开关输入法
     *
     * @param currentFocusView 当前焦点view
     */
    fun toggleSoftInput(currentFocusView: View) {
        val imm = currentFocusView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(currentFocusView, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


    /**
     * 修改当前Windows的softInputMode
     */
    fun updateSoftInputMethod(activity: Activity, softInputMode: Int) {
        if (!activity.isFinishing) {
            val params = activity.window.attributes
            if (params.softInputMode != softInputMode) {
                params.softInputMode = softInputMode
                activity.window.attributes = params
            }
        }
    }

    private const val KEYBOARD: String = "key_board_height"

    @SuppressLint("ApplySharedPref")
    fun setKeyboardHeight(height: Int, cot: Context) {
        val sp = cot.getSharedPreferences("IM_INPUT", 0)
        val editor = sp.edit()
        editor.putInt(KEYBOARD, height)
        editor.commit()
    }

    fun getKeyboardHeight(cot: Context) :Int {
        val sp = cot.getSharedPreferences("IM_INPUT", 0)
        return sp.getInt(KEYBOARD,sKeyBoardHeight)
    }
}
