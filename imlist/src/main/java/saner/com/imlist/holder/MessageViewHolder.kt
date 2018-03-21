package saner.com.imlist.holder

import android.support.v7.widget.RecyclerView
import android.view.View


open class MessageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun getConvertView(): View {
        return itemView
    }
}