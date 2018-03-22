package saner.com.imlist.helper

import android.support.v7.widget.RecyclerView
import saner.com.imlist.holder.MessageViewHolder

/**
 *Helper基类
 */
abstract class MessageViewHelper<out ADAPTER : RecyclerView.Adapter<MessageViewHolder>, in HOLDER : MessageViewHolder, in DATA>(adapter: ADAPTER){

    private val mAdapter:ADAPTER = adapter

     fun getAdapter() : ADAPTER{
        return mAdapter
    }

    //传递Adapter中的数据到Helper中
    abstract fun convert(holder: HOLDER, data: DATA, position: Int)
}