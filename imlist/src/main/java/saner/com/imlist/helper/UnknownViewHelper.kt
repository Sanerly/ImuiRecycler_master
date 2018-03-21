package saner.com.imlist.helper

import android.annotation.SuppressLint
import android.widget.TextView
import saner.com.imlist.R
import saner.com.imlist.adapter.BaseIMRecyclerAdapter
import saner.com.imlist.model.IMessage

/**
 * 消息无法显示时显示该ITEM
 */
class UnknownViewHelper(adapter: BaseIMRecyclerAdapter<IMessage>) : BaseMessageViewHelper(adapter) {
    @SuppressLint("SetTextI18n")
    override fun bindContentView() {
        text.text = "无法显示该‘${mData.getMsgType()}’类型的消息"
    }
    private lateinit var text:TextView
    override fun inflateContentView() {
        text=findViewById(R.id.unknown_content)
    }

    override fun getContentResId(): Int {
        return R.layout.im_unknown_layout
    }

}