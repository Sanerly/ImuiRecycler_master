package com.saner.imuirecycler.list

import com.saner.imuirecycler.model.MyMessage
import saner.com.imlist.adapter.BaseRecyclerAdapter
import saner.com.imlist.model.IMessage

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
