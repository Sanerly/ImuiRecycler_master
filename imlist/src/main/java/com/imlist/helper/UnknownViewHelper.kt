package com.imlist.helper

import android.annotation.SuppressLint
import android.widget.TextView
import saner.com.imlist.R
import com.imlist.adapter.BaseRecyclerAdapter
import com.imlist.model.IMessage

/**
 * 消息无法显示时显示该ITEM
 */
class UnknownViewHelper(adapter: BaseRecyclerAdapter<IMessage>) : BaseMessageViewHelper(adapter) {
    @SuppressLint("SetTextI18n")
    override fun bindContentView() {
        text.text = "无法显示该‘${mMessage.getMsgType()}’类型的消息"
    }
    private lateinit var text:TextView
    override fun inflateContentView() {
        text=findViewById(R.id.unknown_content)
    }

    override fun getContentResId(): Int {
        return R.layout.im_unknown_layout
    }

}