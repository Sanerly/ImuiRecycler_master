package com.imlist.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet


class MessageImageView: BaseImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun getBuilder(rectF: RectF): Builder? {
        val path = Path()
        path.addRoundRect(rectF, 20f, 20f, Path.Direction.CW)
        return Builder.newInstance().setPath(path).setColor(Color.WHITE).setStrokeWidth(10)
    }

}
