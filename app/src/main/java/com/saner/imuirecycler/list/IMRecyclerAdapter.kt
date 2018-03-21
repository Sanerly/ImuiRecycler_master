package com.saner.imuirecycler.list

import saner.com.imlist.model.IMessage
import saner.com.imlist.adapter.BaseIMRecyclerAdapter

/**
 * Created by sunset on 2018/3/13.
 */

class IMRecyclerAdapter(datas: ArrayList<IMessage>) : BaseIMRecyclerAdapter<IMessage>(datas) {



    private val mMessages = datas

    init {

    }

    override fun getItemKey(data: IMessage): String {
       return data.getMsgId()
    }

}
