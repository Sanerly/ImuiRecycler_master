package com.saner.imuirecycler.model

import saner.com.imlist.model.IMessage
import saner.com.imlist.model.MessageDirection
import saner.com.imlist.model.MessageType

/**
 * Created by sunset on 2018/3/12.
 */

class MyMessage : IMessage {


    private lateinit var id: String
    private lateinit var type: MessageType
    private lateinit var direction: MessageDirection
    private lateinit var text: String
    private var avatar: String = ""

    override fun getMsgType(): MessageType {

        return type
    }

    override fun getMsgId(): String {

        return id
    }

    override fun getMsgDirection(): MessageDirection {
        return direction
    }

    override fun getMsgText(): String {
        return text
    }

    override fun getUserAvatar(): String {

        return avatar
    }


    fun setMsgId(id: String) {
        this.id = id
    }

    fun setMsgType(type: MessageType) {
        this.type = type
    }

    fun setMsgDirection(direction: MessageDirection) {
        this.direction = direction
    }


    fun setMsgText(text: String) {
        this.text = text
    }


    fun setUserAvatar(avatar: String) {
        this.avatar = avatar
    }
}
