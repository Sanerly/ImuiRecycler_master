package com.saner.imui.list.helper

import com.saner.imui.R
import com.imlist.adapter.BaseRecyclerAdapter
import com.imlist.helper.BaseMessageViewHelper
import com.imlist.model.IMessage
import com.imlist.widget.MessageImageView

/**
 * Created by sunset on 2018/3/14.
 */

class PhotoViewHelper(adapter: BaseRecyclerAdapter<IMessage>) : BaseMessageViewHelper(adapter) {


    private val url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1524830290547&di=f79fdbccdf46d4b15c3442c502c59c1c&imgtype=0&src=http%3A%2F%2Fimg.douxie.com%2Fupload%2Fupload%2F2014%2F09%2F15%2F54169b02a5380.jpg"

    private lateinit var mImagePhoto: MessageImageView


    override fun inflateContentView() {
        mImagePhoto = findViewById(R.id.photo_image)
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
