package saner.com.imlist.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import java.util.*

/**
 * Created by sunset on 2018/3/16.
 */

class FilletImageView :ImageView{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mPaint: Paint = Paint()
    private var mShape: Shape? = null

    private var mRadius: Float = 20.toFloat()
    init {
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        mPaint.isAntiAlias = true
        mPaint.isFilterBitmap = true
        mPaint.color = Color.BLACK
        mPaint.xfermode= PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (mShape == null) {
            val radius = FloatArray(8)
            Arrays.fill(radius, mRadius)
            mShape = RoundRectShape(radius, null, null)
        }
        mShape!!.resize(width.toFloat(), height.toFloat())
    }
    override fun onDraw(canvas: Canvas) {
        val saveCount = canvas.saveCount
        canvas.save()
        super.onDraw(canvas)
        if (mShape != null) {
            mShape!!.draw(canvas, mPaint)
        }
        canvas.restoreToCount(saveCount)
    }


}
