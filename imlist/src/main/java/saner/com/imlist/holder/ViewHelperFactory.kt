package saner.com.imlist.holder

import saner.com.imlist.helper.BaseMessageViewHelper
import saner.com.imlist.helper.TextViewHelper
import saner.com.imlist.helper.UnknownViewHelper
import saner.com.imlist.model.IMessage
import saner.com.imlist.model.MessageType
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by sunset on 2018/3/12.
 */
class ViewHelperFactory {

    companion object {
        private val viewHelpers: HashMap<MessageType, Class<out BaseMessageViewHelper>> = HashMap()

        /**
         * 注册消息类型
         */
        fun register(messageType: MessageType, viewhelper: Class<out BaseMessageViewHelper>) {
            viewHelpers.put(messageType, viewhelper)
        }


        /**
         * 获取所有继承自BaseMessageViewHelper的子类
         */
        fun getAllViewHolders(): List<Class<out BaseMessageViewHelper>> {
            val list = ArrayList<Class<out BaseMessageViewHelper>>()
            list.add(TextViewHelper::class.java)
            list.add(UnknownViewHelper::class.java)
            when {
                viewHelpers.size > 0 -> list.addAll(viewHelpers.values)
            }
            return list
        }

        /**
         * 不同的消息类型返回不同的Helper
         */
        fun getViewHolderByType(message: IMessage): Class<out BaseMessageViewHelper> {
            when {
                message.getMsgType() == MessageType.text -> return TextViewHelper::class.java
                else -> {
                    var helper: Class<out BaseMessageViewHelper>? = null
                    while (helper == null && viewHelpers.size>0) {
                        helper = viewHelpers[message.getMsgType()]
                    }
                    return if (helper==null) UnknownViewHelper::class.java else  helper
                }
            }

        }
    }
}


