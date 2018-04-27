package saner.com.imlist.widget

import android.content.Context
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet

/**
 * Created by sunset on 2018/4/27.
 */

class AvatarImageView:BaseImageView{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun getBuilder(rectF: RectF): Builder? {
        val path = Path()
        path.addOval(rectF, Path.Direction.CW)
        return Builder.newInstance().setPath(path)
    }

}
