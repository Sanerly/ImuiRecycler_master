package saner.com.imlist.model.interfaces

import android.support.v7.widget.RecyclerView
import saner.com.imlist.model.IMessage

/**
 * Created by sunset on 2018/3/14.
 */

interface IMListEventListener {
    /**
     * 点击好友头像通知
     */
    fun onRightAvatar(message: IMessage)

    /**
     * 点击自己头像通知
     */
    fun onLeftAvatar(message: IMessage)

    /**
     * 列表Item点击通知
     */
    fun onItemClick(message: IMessage)

    /**
     * 列表Item长按通知
     */
    fun onItemLongClick(message: IMessage)

    /**
     * 下拉加载更多
     */
    fun onLoadMore()

    /**
     * RecyclerView 上下滑动,控制列表往下滑动时软件盘隐藏
     */
    fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int)
}
