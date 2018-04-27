package com.iminput.util

import android.util.Log

object LogUtil {
    private const val LOG_TAG: String = "IM_UI_INPUT"
    fun loge(string: String) {
        Log.e(LOG_TAG, string)
    }

    fun logd(string: String) {
        Log.d(LOG_TAG, string)
    }

    fun logi(string: String) {
        Log.i(LOG_TAG, string)
    }

}