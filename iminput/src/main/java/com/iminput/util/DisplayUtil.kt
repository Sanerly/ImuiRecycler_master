package com.iminput.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

import com.iminput.R

/**
 * @author xh2009cn
 */
object DisplayUtil {
    private const val ROUND_CEIL = 0.5f
    private var sDisplayMetrics: DisplayMetrics? = null
    private var sResources: Resources? = null
    /**
     * 获取默认软键盘高度 单位：像素
     *
     * @return 默认软键盘高度
     */
    var defaultKeyboardHeight: Int = 0
        private set

    /**
     * 获取屏幕宽度 单位：像素
     *
     * @return 屏幕宽度
     */
    val screenWidth: Int
        get() = sDisplayMetrics!!.widthPixels

    /**
     * 获取屏幕高度 单位：像素
     *
     * @return 屏幕高度
     */
    val screenHeight: Int
        get() = sDisplayMetrics!!.heightPixels

    /**
     * 获取屏幕宽度 单位：像素
     *
     * @return 屏幕宽度
     */
    val density: Float
        get() = sDisplayMetrics!!.density

    /**
     * 获取状态栏高度
     * @return 状态栏高度
     */
    val statusBarHeight: Int
        @SuppressLint("PrivateApi")
        get() {
            val defaultHeightInDp = 19
            var height = DisplayUtil.dp2px(defaultHeightInDp)
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val obj = c.newInstance()
                val fields = c.getField("status_bar_height")
                height = sResources!!.getDimensionPixelSize(Integer.parseInt(fields.get(obj).toString()))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return height
        }

    /**
     * 初始化操作
     *
     * @param context context
     */
    fun init(context: Context) {
        sDisplayMetrics = context.resources.displayMetrics
        sResources = context.resources
        defaultKeyboardHeight = sResources!!.getDimensionPixelSize(R.dimen.default_keyboard_height)
    }

    /**
     * dp 转 px
     *
     * @param dp dp值
     * @return 转换后的像素值
     */
    fun dp2px(dp: Int): Int {
        return (dp * sDisplayMetrics!!.density + ROUND_CEIL).toInt()
    }

    /**
     * dp 转 px
     *
     * @param dp dp值
     * @return 转换后的像素值
     */
    fun dp2px(dp: Float): Float {
        return dp * sDisplayMetrics!!.density + ROUND_CEIL
    }

    /**
     * px 转 dp
     *
     * @param px px值
     * @return 转换后的dp值
     */
    fun px2dp(px: Int): Int {
        return (px / sDisplayMetrics!!.density + ROUND_CEIL).toInt()
    }
}
