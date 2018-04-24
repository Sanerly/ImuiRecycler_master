package saner.com.imlist.interfaces


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class ScrollMoreListener(layoutManager: LinearLayoutManager, mListener: OnLoadMoreListener?) : RecyclerView.OnScrollListener() {

    private val mLayoutManager = layoutManager
    private val listener = mListener
    private var mPreviousTotalItemCount = 0
    private var mLoading = false

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        listener?.onScrolled(recyclerView, dx, dy)
    }


    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (listener != null) {
            val totalItemCount = mLayoutManager.itemCount
            val lastVisibleItemPosition  = mLayoutManager.findLastVisibleItemPosition()

            if (totalItemCount < mPreviousTotalItemCount) {
                if (totalItemCount == 0) {
                    mLoading = true
                }
            }

            if (mLoading && totalItemCount > mPreviousTotalItemCount) {
                mLoading = false
            }
            val visibleThreshold = 3
            if (!mLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
                mPreviousTotalItemCount = totalItemCount
                mLoading = true
                listener.onLoadMore()

            }
        }
    }

    interface OnLoadMoreListener {
        /**
         * 下拉加载更多
         */
        fun onLoadMore()

        /**
         * RecyclerView 上下滑动
         */
        fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int)
    }
}
