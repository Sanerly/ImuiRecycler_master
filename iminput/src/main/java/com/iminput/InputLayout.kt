package com.iminput

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.iminput.listener.InputListener
import com.iminput.util.DisplayUtil
import com.iminput.util.InputUtil


/**
 * Created by sunset on 2018/3/22.
 */
class InputLayout : LinearLayout, View.OnClickListener, View.OnTouchListener {


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
    private lateinit var inputSend:Button


    private lateinit var inputEditMessage: EditText
    private var sleep: Long = 500

    private lateinit var mInputListener:InputListener

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
        inputSend=this.findViewById(R.id.input_send)

        inputMore.setOnClickListener(this)
        inputEmoji.setOnClickListener(this)
        inputEditMessage.setOnTouchListener(this)

        //获取缓存中的软键盘高度
        val height = InputUtil.getKeyboardHeight(context)
        setContainerHeight(height)

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_UP) {
            if (isContainerShowing()) {
                inputContainer.postDelayed(mHideContainerRunnable, sleep)
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.input_more -> {
                if (isContainerShowing() && isMoreShowing()) {
                    switchKeyboard()
                } else {
                    showContainer()
                    setContainerLayout(false)
                }
            }
            R.id.input_emoji -> {

                if (isContainerShowing() &&  isEmojiShowing()) {
                    switchKeyboard()
                } else {
                    showContainer()
                    setContainerLayout(true)
                }

            }
        }
    }




    /**
     * 切换软键盘可见和隐藏
     */
    private  fun switchKeyboard() {
        InputUtil.toggleSoftInput(inputEditMessage)
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
     * 判断更多是否正在可见状态
     */
    private fun isMoreShowing(): Boolean {
        return inputMoreLayout.visibility == View.VISIBLE
    }

    /**
     * 判断表情是否正在可见状态
     */
    private fun isEmojiShowing(): Boolean {
        return inputEmojiLayout.visibility == View.VISIBLE
    }

    /**
     * 判断容器是否可见
     */
    private fun isContainerShowing(): Boolean {
        return inputContainer.visibility == View.VISIBLE
    }

    /**
     * 显示容器
     */
    private fun showContainer() {
        inputContainer.removeCallbacks(mHideContainerRunnable)
        InputUtil.updateSoftInputMethod((context as Activity), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        inputContainer.visibility = View.VISIBLE
        InputUtil.hideKeyboard(inputEditMessage)
    }

    /**
     * 隐藏容器
     */
    private fun hideContainer() {
        if (isContainerShowing()) {
            inputContainer.visibility = View.GONE
            InputUtil.updateSoftInputMethod((context as Activity), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    /**
     * 设置容器高度
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
     * 计算软键盘的高度
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
                InputUtil.setKeyboardHeight(mKeyBoardHeight, context)
                setContainerHeight(mKeyBoardHeight)
                isHas = true
            }
        }
        mLayoutBottom = b
    }


    /**
     *关闭软键盘和容器布局
     */
     fun hideInputLayout(){
        InputUtil.hideKeyboard(inputEditMessage)
        inputContainer.postDelayed(mHideContainerRunnable, 0)
    }

    /**
     * 设置Input布局监听
     */
    fun setInputListener(listener: InputListener){
        this.mInputListener=listener
    }


}

