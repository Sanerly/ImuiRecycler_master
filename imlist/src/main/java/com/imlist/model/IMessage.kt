package com.imlist.model

/**
 * Created by sunset on 2018/3/12.
 */
interface IMessage {
    /**
     * 消息Id
     * @return unique
     */
    fun getMsgId(): String

    /**
     * 消息类型
     */
    fun getMsgType(): MessageType


    /**
     * 消息方向
     */
    fun getMsgDirection(): MessageDirection

    /**
     * 文本消息
     */
    fun getMsgText():String

    /**
     * 获取头像
     */
    fun getUserAvatar():String

}