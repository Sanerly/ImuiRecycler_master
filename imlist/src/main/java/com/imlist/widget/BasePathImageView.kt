package com.imlist.widget


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.imlist.util.LogUtil


abstract class BasePathImageView :ImageView{

    private var mPaint: Paint = Paint()
    private var mRectF: RectF = RectF()


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        mPaint.isAntiAlias = true
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF.left = paddingLeft.toFloat()
        mRectF.top = paddingTop.toFloat()
        mRectF.right = (w - paddingRight).toFloat()
        mRectF.bottom = (h - paddingBottom).toFloat()
    }
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val builder = getBuilder(mRectF)
        if (builder == null) {
            LogUtil.logd("no builder")
            return
        }
        val path = builder.path
        canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG)
        super.onDraw(canvas)
        onClipDraw(builder, path, canvas)
    }

    private fun onClipDraw(builder: Builder, path: Path?, canvas: Canvas) {
        if (builder.strokeWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉  在源图上面抠出一个目标图形
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            mPaint.color = Color.WHITE
            mPaint.strokeWidth = (builder.strokeWidth * 2).toFloat()
            mPaint.style = Paint.Style.STROKE
            canvas.drawPath(path, mPaint)
            // 绘制描边  在刚刚抠出的目标图形上绘制边框
            mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
            mPaint.color = builder.color
            mPaint.style = Paint.Style.STROKE
            canvas.drawPath(path, mPaint)
        }
        // 混合模式为 DST_IN, 即仅显示当前绘制区域和背景区域交集的部分，并仅显示背景内容。
        //裁剪掉目标图以外的内容
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL
        canvas.drawPath(path, mPaint)
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
