package saner.com.imlist.helper

import android.widget.TextView
import saner.com.imlist.R
import saner.com.imlist.adapter.BaseIMRecyclerAdapter
import saner.com.imlist.model.IMessage

/**
 * Created by sunset on 2018/3/13.
 */

class TextViewHelper(adapter: BaseIMRecyclerAdapter<IMessage>) : BaseMessageViewHelper(adapter) {

    lateinit var text: TextView

    override fun inflateContentView() {
        text = findViewById(R.id.text_content)
    }

    override fun bindContentView() {
//        LogUtil.logd(mData.getMsgText())
        initTextColor()
        text.text = mData.getMsgText()
    }

    private fun initTextColor() {
        if (isMsgDirection()){
            text.setTextColor(mContext.resources.getColor(R.color.black))
        }else{
            text.setTextColor(mContext.resources.getColor(R.color.white))
        }
    }


    override fun getContentResId(): Int {

        return R.layout.im_text_layout
    }


}
