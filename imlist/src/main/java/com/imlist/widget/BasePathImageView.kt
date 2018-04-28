package com.imlist.widget


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.imlist.util.LogUtil


abstract class BasePathImageView :ImageView{

    private var mPaint: Paint? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.color = Color.TRANSPARENT
        mPaint!!.style = Paint.Style.FILL

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val builder = getBuilder(rectF)
        if (builder == null) {
            LogUtil.logd("no builder")
            return
        }
        val path = builder.path
        canvas.clipPath(path!!)
        canvas.drawPath(path, mPaint!!)
        super.onDraw(canvas)

        if (builder.color == 0 || builder.strokeWidth == 0) {
            LogUtil.loge("current color is null or strokeWidth is 0")
            return
        }
        mPaint!!.strokeWidth = builder.strokeWidth.toFloat()
        mPaint!!.color = builder.color
        mPaint!!.style = Paint.Style.STROKE
        canvas.drawPath(path, mPaint!!)
    }

    abstract fun getBuilder(rectF: RectF): Builder?

    class Builder {
        var path: Path? = null
        var strokeWidth = 0
        var color = 0
        fun setPath(path: Path): Builder {
            this.path = path
            return this
        }

        fun setStrokeWidth(strokeWidth: Int): Builder {
            this.strokeWidth = strokeWidth
            return this
        }

        fun setColor(color: Int): Builder {
            this.color = color
            return this
        }

        companion object {
            internal fun newInstance(): Builder {
                return Builder()
            }
        }
    }

}
