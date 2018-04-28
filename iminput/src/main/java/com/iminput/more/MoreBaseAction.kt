package com.iminput.widget.more

import com.iminput.listener.InputListener

/**
 * Created by sunset on 2018/4/26.
 */

abstract class MoreBaseAction constructor(title: String, icon: Int) {

    private var title:String=title
    private var icon:Int=icon
    protected var mListener: InputListener? = null



    init {

    }

    fun getTitle():String=this.title

    fun getIcon():Int=this.icon

    fun setListener(listener:InputListener){
        this.mListener=listener
    }

    abstract  fun onClick()


}