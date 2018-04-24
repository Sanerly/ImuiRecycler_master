package saner.com.imlist.interfaces

import android.widget.ImageView

/**
 * 消息类型是图片时调用
 */
interface Imageloader {
    /**
     * 提供外部加载图片调用
     */
    fun loadImage(imageView: ImageView,url:String)
}