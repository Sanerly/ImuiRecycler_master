package saner.com.imlist.widget

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import saner.com.imlist.adapter.BaseIMRecyclerAdapter
import saner.com.imlist.model.IMessage
import saner.com.imlist.model.interfaces.ScrollMoreListener

/**
 * Created by sunset on 2018/3/12.
 */
class IMRecyclerView : RecyclerView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    fun <T : IMessage> setAdapter(adapter: BaseIMRecyclerAdapter<T>) {
        val manager = LinearLayoutManager(context)
        manager.reverseLayout=true
        layoutManager = manager

        val itemAnim = DefaultItemAnimator()
        itemAnim.supportsChangeAnimations = false
        itemAnimator=itemAnim

        adapter.setLayoutManager(manager)
        adapter.setContext(context)
        addOnScrollListener(ScrollMoreListener(manager,adapter))
        super.setAdapter(adapter)
    }

     fun toPosition(index:Int){
        scrollToPosition(index)
    }




}