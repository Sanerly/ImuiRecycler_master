package saner.com.imlist.util

import android.util.Log

/**
 * Created by sunset on 2018/3/14.
 */
class LogUtil {

    companion object {
        private val LOG_TAG:String="IM_UI_LIST"
        fun loge(string: String){
            Log.e(LOG_TAG,string)
        }

        fun logd(string: String){
            Log.d(LOG_TAG,string)
        }

        fun logi(string: String){
            Log.i(LOG_TAG,string)
        }

    }
}