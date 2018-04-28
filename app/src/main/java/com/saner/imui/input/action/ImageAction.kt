package com.saner.imui.input.action

import com.iminput.util.LogUtil
import com.iminput.widget.more.MoreBaseAction
import com.saner.imui.R
import com.saner.imui.model.MyMessage
import com.imlist.model.MessageDirection
import com.imlist.model.MessageType

/**
 * Created by sunset on 2018/4/26.
 */

class ImageAction: MoreBaseAction("图片", R.drawable.ic_chat_photo) {


    override fun onClick() {
        mListener!!.onMoreSend(getMessage(0, MessageDirection.In, MessageType.image, ""))
        LogUtil.logd("图片")
    }

    private fun getMessage(i: Int, direction: MessageDirection, messageType: MessageType, content: String): MyMessage {
        val message = MyMessage()
        message.setMsgId(i.toString())
        message.setMsgType(messageType)
        message.setMsgText("$i   $content")
        message.setMsgDirection(direction)
        if (MessageDirection.Out == direction) {
            message.setUserAvatar("http://img3.imgtn.bdimg.com/it/u=1486014782,3060017975&fm=27&gp=0.jpg")
        } else {
            message.setUserAvatar("http://img.besoo.com/file/201705/07/0807581245908.jpg")
        }
        return message
    }
}
