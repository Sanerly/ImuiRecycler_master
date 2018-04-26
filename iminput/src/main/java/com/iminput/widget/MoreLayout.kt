package com.iminput.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.iminput.R
import com.iminput.util.LogUtil
import kotlinx.android.synthetic.main.more_layout.view.*

/**
 * Created by sunset on 2018/4/26.
 */

class MoreLayout:FrameLayout,ViewPager.OnPageChangeListener{


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    private  var mActions: ArrayList<MoreBaseAction>
    private  val mAdapter:MorePagerAdapter

    init {
        View.inflate(this.context,R.layout.more_layout,this)
        mActions=ArrayList()
        mAdapter= MorePagerAdapter(mActions)
        mViewPager.adapter=mAdapter
    }


    fun setActions(actions: ArrayList<MoreBaseAction>){
        mActions.addAll(actions)
        mAdapter.notifyDataSetChanged()
    }


    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        LogUtil.logd("position = $position")
    }
}
