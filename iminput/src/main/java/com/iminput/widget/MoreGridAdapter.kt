package com.iminput.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.iminput.R

/**
 * Created by sunset on 2018/4/26.
 */
class MoreGridAdapter(actions: List<MoreBaseAction>) : BaseAdapter() {


    private var mActions: List<MoreBaseAction> = actions
    init {
    }

    override fun getItem(position: Int): Any = mActions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mActions.size


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val mHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.more_grid_item, null)
            mHolder = ViewHolder(view)
            view.tag = mHolder
        } else {
            view = convertView
            mHolder = view.tag as ViewHolder
        }

        mHolder.mGridItemImage?.setImageResource(mActions[position].getIcon())
        mHolder.mGridItemText?.text = mActions[position].getTitle()


        return view
    }



    class ViewHolder(itemView: View) {
        var mGridItemImage: ImageView? = null
        var mGridItemText: TextView? = null

        init {
            mGridItemImage = itemView.findViewById(R.id.mGridItemImage)
            mGridItemText = itemView.findViewById(R.id.mGridItemText)
        }

    }
}