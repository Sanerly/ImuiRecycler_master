package com.iminput.widget

import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by sunset on 2018/4/26.
 */
class MorePagerAdapter(actions: ArrayList<MoreBaseAction>) : PagerAdapter() {
    private var mActions: ArrayList<MoreBaseAction> = actions
    private  val ITEM_COUNT_PER_GRID_VIEW = 8

    private  var itemCount:Int
    init {
        itemCount= (mActions.size + ITEM_COUNT_PER_GRID_VIEW - 1) / ITEM_COUNT_PER_GRID_VIEW;
    }


    override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view == `object`

    override fun getCount(): Int = itemCount

    override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val tv:TextView= TextView(container.context)

        tv.setTextColor(Color.WHITE)
        tv.text="HAHAHAHAH"
        return tv
    }
}