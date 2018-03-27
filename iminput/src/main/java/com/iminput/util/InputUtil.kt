package com.iminput.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.annotation.SuppressLint


/**
 * @author xh2009cn
 */
object InputUtil {


    private const val KEYBOARD: String = "key_board_height"

    private var sKeyBoardHeight = DisplayUtil.defaultKeyboardHeight


    /**
     * 隐藏输入法
     */
    fun hideKeyboard(view: View?) {
        if (view != null) {
            val token = view.windowToken
            if (token != null) {
                val im = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(token, 0)
            }
        }
    }

    /**
     * 开关输入法
     */
    fun toggleSoftInput(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


    /**
     * 更新当前Windows的softInputMode
     */
    fun updateSoftInputMethod(activity: Activity, mode: Int) {
        if (!activity.isFinishing) {
            val params = activity.window.attributes
            if (params.softInputMode != mode) {
                params.softInputMode = mode
                activity.window.attributes = params
            }
        }
    }


    /**
     * 储存软键盘的高度
     */
    @SuppressLint("ApplySharedPref")
    fun setKeyboardHeight(height: Int, cot: Context) {
        val sp = cot.getSharedPreferences("IM_INPUT", 0)
        val editor = sp.edit()
        editor.putInt(KEYBOARD, height)
        editor.commit()
    }

    /**
     * 取出软键盘的高度
     */
    fun getKeyboardHeight(cot: Context) :Int {
        val sp = cot.getSharedPreferences("IM_INPUT", 0)
        return sp.getInt(KEYBOARD,sKeyBoardHeight)
    }
}
