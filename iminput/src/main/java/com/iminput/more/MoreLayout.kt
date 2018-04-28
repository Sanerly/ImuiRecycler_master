package com.iminput.widget.more

import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.iminput.R
import com.iminput.util.InputLayoutUtil
import kotlinx.android.synthetic.main.more_layout.view.*

/**
 * 更多容器实现
 */
class MoreLayout : FrameLayout, ViewPager.OnPageChangeListener {


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mActions: ArrayList<MoreBaseAction>
    private val mIndicators: ArrayList<ImageView>
    private lateinit var mAdapter: MorePagerAdapter
    private val mHeight: Int

    init {
        View.inflate(this.context, R.layout.more_layout, this)
        mActions = ArrayList()
        mIndicators = ArrayList()
        mHeight = InputLayoutUtil.getKeyboardHeight(context)
    }

    /**
     * 设置更多布局的集合
     */
    fun setActions(actions: ArrayList<MoreBaseAction>) {
        mActions.addAll(actions)
        mAdapter = MorePagerAdapter(mActions)
        mViewPager.adapter = mAdapter
        mViewPager.addOnPageChangeListener(this)
        addIndicators()
        switchIndicatorColor(0)
    }


    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        switchIndicatorColor(position)

    }

    /**
     * 添加指示器
     */
    private fun addIndicators() {
        if (mAdapter.count < 2) return
        for (i in 0 until mAdapter.count) {
            val image = ImageView(this.context)
            image.setBackgroundColor(Color.GRAY)
            val params = LinearLayout.LayoutParams(15, 15)
            params.setMargins(5, 5, 5, 5)
            image.layoutParams = params
            mIndicator.addView(image)
            mIndicators.add(image)
        }
    }

    /**
     * 切换指示器
     */
    private fun switchIndicatorColor(position: Int) {
        if (mIndicators.size < 1) return
        for (i in mIndicators.indices) {
            val image: ImageView = mIndicators[i]
            if (i == position) {
                image.setBackgroundColor(Color.RED)
            } else {
                image.setBackgroundColor(Color.GRAY)
            }
        }
    }
}
