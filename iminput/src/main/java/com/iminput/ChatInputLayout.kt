package com.iminput

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.iminput.util.LogUtil


/**
 * Created by sunset on 2018/3/22.
 */

class ChatInputLayout : LinearLayout {

    private  var mKeyBoardHeight:Int=0


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        layoutParams= LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        View.inflate(context,R.layout.chat_input_layout,this)

    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mKeyBoardHeight<b){
            LogUtil.logd("隐藏" )
        }else{
            LogUtil.logd("显示" )
        }
        if (mKeyBoardHeight!=0){
            LogUtil.logd("bottom = ${mKeyBoardHeight-b}" )
        }
        mKeyBoardHeight=b
        LogUtil.logd("bottom = $b" )

    }
}
