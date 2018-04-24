package com.iminput.widget;

/**
 * Created by sunset on 2018/4/24.
 */

public enum VoicePressEnum {
    /**
     * 松开发送
     */
    VOICE_RELEASE_SEND("voice_release_send"),
    /**
     * 松开取消
     */
    VOICE_RELEASE_CANCEL("voice_release_cancel"),
    /**
     * 按下说话
     */
    VOICE_PRESS_SPEAK("voice_press_speak");

    private String value;

    VoicePressEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
