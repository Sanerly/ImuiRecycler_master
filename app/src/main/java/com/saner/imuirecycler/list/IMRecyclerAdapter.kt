package com.saner.imuirecycler.list

import saner.com.imlist.adapter.BaseRecyclerAdapter
import saner.com.imlist.model.IMessage

/**
 * Created by sunset on 2018/3/13.
 */

class IMRecyclerAdapter(messages: ArrayList<IMessage>) : BaseRecyclerAdapter<IMessage>(messages) {

    init {


    }

    override fun getItemKey(data: IMessage): String {
       return data.getMsgId()
    }

}
