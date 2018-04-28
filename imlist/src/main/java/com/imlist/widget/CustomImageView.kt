package saner.com.imlist.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.graphics.RectF


/**
 * Created by sunset on 2018/3/16.
 */

class CustomImageView : BaseImageView {

    private var mRadius = 10.toFloat()

    private var styleType: Int = CIRCLE_TYPE

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    companion object {
        const val CIRCLE_TYPE: Int = 0
        const val ROUND_TYPE: Int = 1
        const val RECT_TYPE: Int = 2
    }


    override fun getBitmap(): Bitmap? {
        val bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        when (styleType) {
            CIRCLE_TYPE -> canvas.drawCircle(width / 2f, width / 2f, width / 2f, paint)
            ROUND_TYPE -> canvas.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), mRadius, mRadius, paint)
            RECT_TYPE -> canvas.drawRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), paint)
        }

        return bitmap
    }

    fun setStyleTyoe(type: Int) {
        this.styleType = type
    }

    fun setBorderRadius(radius: Float) {
        this.mRadius = radius
    }
}
