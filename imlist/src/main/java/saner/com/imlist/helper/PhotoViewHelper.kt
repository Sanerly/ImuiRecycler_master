package saner.com.imlist.helper

import saner.com.imlist.R
import saner.com.imlist.adapter.BaseRecyclerAdapter
import saner.com.imlist.model.IMessage
import saner.com.imlist.widget.CustomImageView

/**
 * Created by sunset on 2018/3/14.
 */

class PhotoViewHelper(adapter: BaseRecyclerAdapter<IMessage>) : BaseMessageViewHelper(adapter) {


    private val url = "http://h.hiphotos.baidu.com/zhidao/pic/item/43a7d933c895d1438a696fa475f082025aaf071d.jpg"

    private lateinit var mImagePhoto: CustomImageView


    override fun inflateContentView() {
        mImagePhoto = findViewById(R.id.photo_image)
        mImagePhoto.setStyleTyoe(CustomImageView.ROUND_TYPE)
        mImagePhoto.setBorderRadius(15.toFloat())
    }

    override fun bindContentView() {
        val params = mImagePhoto.layoutParams
        val dm = mContext.resources.displayMetrics
        params.width = dm.widthPixels / 3
        params.height = dm.heightPixels / 4
        getAdapter().getImageLoader().loadImage(mImagePhoto, url)
    }

    override fun getContentResId(): Int {
        return R.layout.im_photo_layout
    }

    override fun leftBackground(): Int {
        return 0
    }

    override fun rightBackground(): Int {
        return 0
    }
}
