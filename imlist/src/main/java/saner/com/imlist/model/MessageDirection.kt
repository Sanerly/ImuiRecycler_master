package saner.com.imlist.model

/**
 * Created by sunset on 2018/3/13.
 */
enum class MessageDirection(var3: Int) {
    /**
     * 发出去的消息
     */
    Out(0),
    /**
     * 接收到的消息
     */
    In(1);

    private val value: Int = var3

    fun getValue(): Int {
        return this.value
    }

}