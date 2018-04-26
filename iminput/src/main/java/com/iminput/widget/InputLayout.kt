package com.iminput.widget

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.iminput.R
import com.iminput.listener.IPressStatusListener
import com.iminput.listener.InputListener
import com.iminput.util.DisplayUtil
import com.iminput.util.InputLayoutUtil


/**
 * Created by sunset on 2018/3/22.
 */
class InputLayout : LinearLayout, View.OnClickListener, View.OnTouchListener, TextWatcher, View.OnFocusChangeListener, IPressStatusListener {


    private var isShow: Boolean = false
    //是否计算软键盘高度
    private var isHas: Boolean = false
    private var mShowHeight: Int = 0
    private var mHideHeight: Int = 0
    private var mLayoutBottom: Int = 0
    private var mKeyBoardHeight: Int = 0

    private lateinit var inputMore: ImageView
    private lateinit var inputEmoji: ImageView
    private lateinit var inputContainer: FrameLayout
    private lateinit var inputMoreLayout: RelativeLayout
    private lateinit var inputEmojiLayout: RelativeLayout
    private lateinit var inputSend: Button
    private lateinit var inputVoice: ImageView
    private lateinit var inputPressSpeak: VoiceButton
    private lateinit var inputEditMessage: EditText

    private var sleep: Long = 500
    /**
     * 对外的接口
     */
    private lateinit var mInputListener: InputListener
    /**
     * 控制输入框和按下说话按钮切换
     * false 时显示输入框，true 显示语音按钮
     */
    private var isPress: Boolean = false


    private var isEmoji: Boolean = true

    private var mVoiceLayout: VoiceWindow? = null


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.chat_input_layout, this)
        DisplayUtil.init(context)
        initView()
    }


    private fun initView() {
        inputEditMessage = this.findViewById(R.id.input_edit_message)
        inputMore = this.findViewById(R.id.input_more)
        inputEmoji = this.findViewById(R.id.input_emoji)
        inputContainer = this.findViewById(R.id.input_container)
        inputMoreLayout = this.findViewById(R.id.input_container_more)
        inputEmojiLayout = this.findViewById(R.id.input_container_emoji)
        inputSend = this.findViewById(R.id.input_send)
        inputVoice = this.findViewById(R.id.input_voice)
        inputPressSpeak = this.findViewById(R.id.input_speak_voice)

        inputVoice.setOnClickListener(this)
        inputSend.setOnClickListener(this)
        inputMore.setOnClickListener(this)
        inputEmoji.setOnClickListener(this)
        inputEditMessage.setOnTouchListener(this)
        inputEditMessage.addTextChangedListener(this)
        inputEditMessage.onFocusChangeListener = this
        //设置按下说话的状态监听
        inputPressSpeak.setStatusListener(this)
        //获取缓存中的软键盘高度
        val height = InputLayoutUtil.getKeyboardHeight(context)
        setContainerHeight(height)
        //设置默认显示输入框
        setInputSpeakLayout(isPress)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_UP) {
            if (isContainerVisible()) {
                defaultEmoji()
                inputContainer.postDelayed(mHideContainerRunnable, sleep)
            }
        }
        return false
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.input_more -> {
                if (isVoiceVisible()) {
                    isPress = false
                    setInputSpeakLayout(isPress)
                }
                defaultEmoji()
                if (isContainerVisible() && isMoreVisible()) {
                    switchKeyboard()
                } else {
                    showContainer()
                    setContainerLayout(false)
                }
                hideCursor()
            }
            R.id.input_emoji -> {
                if (isVoiceVisible()) {
                    isPress = false
                    setInputSpeakLayout(isPress)
                }
                if (isContainerVisible() && isEmojiVisible()) {
                    switchKeyboard()
                } else {
                    showContainer()
                    setContainerLayout(true)
                }
                showCursor()
                switchEmojiRes()
            }
            R.id.input_send -> {
                val str = inputEditMessage.text.toString()
                mInputListener.onTextSend(str)
                inputEditMessage.setText("")
            }
            R.id.input_voice -> {
                defaultEmoji()

                switchPressInput()
            }
        }
    }


    /**
     * 默认显示表情图片
     */
    private fun defaultEmoji() {
        isEmoji = false
        switchEmojiRes()
    }


    /**
     * 切换表情按钮的图片--emoji和keyboard
     */
    private fun switchEmojiRes() {
        val srcRes = if (isEmoji) R.drawable.ic_chat_keyboard else R.drawable.ic_chat_emo
        inputEmoji.setImageResource(srcRes)
        isEmoji = !isEmoji
    }


    /**
     * 切换软键盘可见和隐藏
     */
    private fun switchKeyboard() {
        InputLayoutUtil.toggleSoftInput(inputEditMessage)
        inputContainer.postDelayed(mHideContainerRunnable, sleep)
    }

    /**
     * 设置表情和更多布局切换
     */
    private fun setContainerLayout(boolean: Boolean) {
        val show = if (boolean) inputEmojiLayout else inputMoreLayout
        val hide = if (!boolean) inputEmojiLayout else inputMoreLayout
        show.visibility = View.VISIBLE
        hide.visibility = View.GONE
    }


    /**
     * 切换输入框和按下说话的按钮，以及当软键盘弹出时输入框获取焦点
     */
    private fun switchPressInput() {
        if (isPress) {
            hideInputLayout()
        } else {
            isMoreOrSend(false)
            switchKeyboard()
            showCursor()
        }
        setInputSpeakLayout(isPress)
    }

    /**
     * 切换输入框和按下说话的按钮，切换语音图标和键盘图标
     */
    private fun setInputSpeakLayout(boolean: Boolean) {
        val show = if (boolean) inputPressSpeak else inputEditMessage
        val hide = if (!boolean) inputPressSpeak else inputEditMessage
        show.visibility = View.VISIBLE
        hide.visibility = View.GONE
        val srcRes = if (boolean) R.drawable.ic_chat_keyboard else R.drawable.ic_chat_voice
        inputVoice.setImageResource(srcRes)
        isPress = !isPress
    }

    /**
     * 判断更多容器是否正在可见状态
     */
    private fun isMoreVisible(): Boolean = inputMoreLayout.visibility == View.VISIBLE

    /**
     * 判断表情容器是否正在可见状态
     */
    private fun isEmojiVisible(): Boolean = inputEmojiLayout.visibility == View.VISIBLE

    /**
     * 判断一级容器是否可见
     */
    private fun isContainerVisible(): Boolean = inputContainer.visibility == View.VISIBLE

    /**
     * 判断语音按钮是否可见
     */
    private fun isVoiceVisible(): Boolean = inputVoice.visibility == View.VISIBLE

    /**
     * 显示一级容器
     */
    private fun showContainer() {
        inputContainer.removeCallbacks(mHideContainerRunnable)
        InputLayoutUtil.updateSoftInputMethod((context as Activity), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        inputContainer.visibility = View.VISIBLE
        InputLayoutUtil.hideKeyboard(inputEditMessage)
    }

    /**
     * 隐藏一级容器
     */
    private fun hideContainer() {
        if (isContainerVisible()) {
            inputContainer.visibility = View.GONE
            InputLayoutUtil.updateSoftInputMethod((context as Activity), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    /**
     * 设置一级容器高度
     */
    private fun setContainerHeight(keyboardHeight: Int) {
        val params = inputContainer.layoutParams
        if (params != null && params.height != keyboardHeight) {
            params.height = keyboardHeight
            inputContainer.layoutParams = params
        }
    }


    private val mHideContainerRunnable = Runnable { hideContainer() }

    /**
     * 显示输入框的光标
     */
    private fun showCursor() {
        inputEditMessage.postDelayed(mRequestFocusRunnable, sleep)
    }

    /**
     * 隐藏输入框的光标
     */
    private fun hideCursor() {
        inputEditMessage.clearFocus()
    }

    private val mRequestFocusRunnable = Runnable { inputEditMessage.requestFocus() }

    /**
     * 计算软键盘的高度
     *
     * 计算的原理是当软键盘弹起时会将上面的布局顶上去，这样我们拿到被顶上去的布局布局底部到屏幕底部，
     * 之间的距离就是软键盘的距离，第一次我们默认一个大概的高度，当然键盘弹起过一次，就将值保存起来，
     * 留给下次使用。
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mLayoutBottom < b) {
            mHideHeight = b
            isShow = false
        } else {
            mShowHeight = b
            isShow = true
        }
        mInputListener.onSoftKeyboardStatus(isShow)
        if (mShowHeight > 0 && mLayoutBottom > 0) {
            val h = mHideHeight - mShowHeight
            if (h > 0 && !isHas) {
                mKeyBoardHeight = h
                InputLayoutUtil.setKeyboardHeight(mKeyBoardHeight, context)
                setContainerHeight(mKeyBoardHeight)
                isHas = true
            }
        }
        mLayoutBottom = b
    }
    /******************************暴露给外部的方法**************************************/

    /**
     *关闭软键盘和容器布局
     */
    fun hideInputLayout() {
        InputLayoutUtil.hideKeyboard(inputEditMessage)
        inputContainer.postDelayed(mHideContainerRunnable, 0)
    }

    /**
     * 设置Input布局监听
     */
    fun setInputListener(listener: InputListener) {
        this.mInputListener = listener
    }

    /**
     * 设置是否需要语音
     */
    fun isNeedVoice(isNeed: Boolean) {
        inputVoice.visibility = if (isNeed) View.VISIBLE else View.GONE
    }


    fun addVoiceLayout(view: VoiceWindow) {
        mVoiceLayout = view
    }

    /******************************输入框的内容监听**************************************/

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        val length = s!!.length
        if (length > 0) {
            isMoreOrSend(true)
        } else {
            isMoreOrSend(false)
        }

    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        val edit = v as EditText
        if (hasFocus && edit.text.toString().isNotEmpty()) {
            isMoreOrSend(true)
        } else {
            isMoreOrSend(false)
        }
    }

    /**
     * 设置更多按钮和发送按钮交替显示
     * @param isVisi =true表示发送按钮显示 =false 表示发送按钮隐藏
     */
    private fun isMoreOrSend(isVisi: Boolean) {
        val show = if (isVisi) inputSend else inputMore
        val hide = if (isVisi) inputMore else inputSend
        show.visibility = View.VISIBLE
        hide.visibility = View.INVISIBLE
    }

    /******************************发送语音的按钮状态**************************************/

    override fun onStatus(status: VoicePressEnum) {
//        LogUtil.logd(status.value)
        if (mVoiceLayout == null) return
        mVoiceLayout!!.onStatusObserver(mInputListener, status)
    }
}

