package com.saner.imui.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.saner.imui.R

/**
 * Created by sunset on 2018/3/14.
 */
class IMConfig {

    companion object {
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view.context)
                    .load(url)
                    .error(R.mipmap.ic_launcher)
                    .into(view)
        }
    }
}