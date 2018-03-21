package saner.com.imlist.helper

import android.support.v7.widget.RecyclerView
import saner.com.imlist.holder.MessageViewHolder

/**
 * Comparable 同 java extends
 */
abstract class MessageViewHelper<out ADAPTER : RecyclerView.Adapter<MessageViewHolder>, in HOLDER : MessageViewHolder, in DATA>(adapter: ADAPTER){

    private val mAdapter:ADAPTER = adapter

     fun getAdapter() : ADAPTER{
        return mAdapter
    }


    abstract fun convert(holder: HOLDER, data: DATA, position: Int)
}