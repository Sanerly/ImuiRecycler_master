package saner.com.imlist.model

/**
 * Created by sunset on 2018/3/12.
 */
enum class MessageType(var3: Int, var4: String) {
    unknown(-1, "Unknown"),
    text(0, "文本"),
    image(1, "图片"),
    audio(2, "语音"),
    video(3, "视频"),
    location(4, "位置"),
    file(6, "文件"),
    avchat(7, "音视频通话"),
    notification(5, "通知消息"),
    tip(10, "提醒消息"),
    robot(11, "机器人消息"),
    custom(100, "自定义消息");

    private val value: Int = var3
    private val sendMessageTip: String = var4

    fun getValue(): Int {
        return this.value
    }

    fun getSendMessageTip(): String {
        return this.sendMessageTip
    }
}