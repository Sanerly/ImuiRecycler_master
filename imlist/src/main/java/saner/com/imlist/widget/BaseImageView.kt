package saner.com.imlist.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import java.lang.ref.WeakReference

/**
 * Created by sunset on 2018/3/16.
 */
abstract class BaseImageView : ImageView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)

    private var mMaskBitmap: Bitmap? = null
    private var mWeakBitmap: WeakReference<Bitmap>? = null
    private val mContext = context

    private val mPaint = Paint()

    init {
        mPaint.isAntiAlias = true
        mPaint.isFilterBitmap = true
        mPaint.color = Color.BLACK
    }

    override fun invalidate() {
        mWeakBitmap = null
        if (mMaskBitmap != null) mMaskBitmap!!.recycle()
        super.invalidate()
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        if (!isInEditMode){
            val saveCount=canvas!!.saveLayer(0.toFloat(),0.toFloat(), width.toFloat(), height.toFloat(),null,Canvas.ALL_SAVE_FLAG)

            var bitmap: Bitmap? = if (mWeakBitmap != null) mWeakBitmap!!.get() else null
            if (bitmap==null || bitmap.isRecycled){
                val drawable=drawable
                if (drawable!=null){
                    /*源图*/
                    bitmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
                    val bitmapCanvas=Canvas(bitmap)
                    drawable.setBounds(0,0,width,height)
                    drawable.draw(bitmapCanvas)



                    mPaint.reset()
                    mPaint.isFilterBitmap=false
                    mPaint.xfermode=mXfermode

                    /*目标图*/
                    if (mMaskBitmap==null || mMaskBitmap!!.isRecycled){
                        mMaskBitmap=getBitmap()
                    }
                    //绘制最终图形
                    bitmapCanvas.drawBitmap(mMaskBitmap,0.toFloat(),0.toFloat(),mPaint)

                    //bitmap缓存起来，避免每次调用onDraw，分配内存
                    mWeakBitmap = WeakReference(bitmap)
                }
            }

            if (bitmap!=null){
                mPaint.xfermode=null
                canvas.drawBitmap(bitmap,0.toFloat(),0.toFloat(),mPaint)
            }
            canvas.restoreToCount(saveCount)
        }else{
            super.onDraw(canvas)
        }

    }

    abstract fun getBitmap(): Bitmap?
}