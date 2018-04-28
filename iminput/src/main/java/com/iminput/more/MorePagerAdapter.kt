package com.iminput.widget.more

import android.support.v4.view.PagerAdapter
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import java.util.*


/**
 * Created by sunset on 2018/4/26.
 */
class MorePagerAdapter(actions: ArrayList<MoreBaseAction>) : PagerAdapter() {
    private var mActions: ArrayList<MoreBaseAction> = actions
    private val ITEM_COUNT = 8

    private var itemCount: Int

    init {
        itemCount = (mActions.size + ITEM_COUNT - 1) / ITEM_COUNT
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any?) {
    }

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean = view == `object`

    override fun getCount(): Int = itemCount

    override fun getItemPosition(`object`: Any?): Int = PagerAdapter.POSITION_NONE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val end = if ((position + 1) * ITEM_COUNT > mActions.size) mActions.size else (position + 1) * ITEM_COUNT
        val subBaseActions = mActions.subList(position * ITEM_COUNT, end)
        val gridView = GridView(container.context)
        val mGridAdapter = MoreGridAdapter(subBaseActions)
        gridView.adapter = mGridAdapter
        gridView.numColumns = 4
        gridView.setSelector(android.R.color.transparent)
        gridView.horizontalSpacing = 0
        gridView.verticalSpacing = 0
        gridView.gravity = Gravity.CENTER

        gridView.setOnItemClickListener { _, _, position, _ ->
            subBaseActions[position].onClick()
        }
        container.addView(gridView)
        return gridView
    }


}