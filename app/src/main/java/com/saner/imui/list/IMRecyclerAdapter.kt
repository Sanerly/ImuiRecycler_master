package com.saner.imui.list

import com.saner.imui.model.MyMessage
import com.imlist.adapter.BaseRecyclerAdapter

/**
 * Created by sunset on 2018/3/13.
 */

class IMRecyclerAdapter(messages: ArrayList<MyMessage>) : BaseRecyclerAdapter<MyMessage>(messages) {


    init {
    }

    override fun getItemKey(data: MyMessage): String {
       return data.getMsgId()
    }

}
