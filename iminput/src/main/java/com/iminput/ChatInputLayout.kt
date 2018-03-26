package com.iminput

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.iminput.util.DisplayUtils
import com.iminput.util.InputMethodUtils
import com.iminput.util.LogUtil


/**
 * Created by sunset on 2018/3/22.
 */
class ChatInputLayout : LinearLayout, View.OnClickListener, View.OnTouchListener {


    private var isShow: Boolean = false
    //是否计算软键盘高度
    private var isHas: Boolean = false
    private var mShowHeight: Int = 0
    private var mHideHeight: Int = 0
    private var mLayoutBottom: Int = 0
    private var mKeyBoardHeight: Int = 0

    private lateinit var inputMore: ImageView
    private lateinit var inputMoreContainer: RelativeLayout
    private lateinit var inputEditMessage: EditText
    private var sleep: Long = 500

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.chat_input_layout, this)
        DisplayUtils.init(context)
        initView()
    }


    private fun initView() {
        inputMore = this.findViewById(R.id.input_more)
        inputMoreContainer = this.findViewById(R.id.input_more_layout)
        inputEditMessage = this.findViewById(R.id.input_edit_message)

        inputMore.setOnClickListener(this)
        inputEditMessage.setOnTouchListener(this)


        val height = InputMethodUtils.getKeyboardHeight(context)
        onUpdateContainerHeight(height)

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_UP) {
            if (isContainerShowing()) {
                inputMoreContainer.postDelayed(mHideEmotionPanelTask, sleep)
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.input_more -> {
                LogUtil.logd("触摸更多时容器的状态 ${isContainerShowing()}")
                if (isContainerShowing()) {
                    InputMethodUtils.toggleSoftInput(inputEditMessage)
                    inputMoreContainer.postDelayed(mHideEmotionPanelTask, sleep)
                } else {
                    showContainer()
                }
            }
        }
    }


    /**
     * 判断容器是否可见
     */
    private fun isContainerShowing(): Boolean {
        return inputMoreContainer.visibility == View.VISIBLE
    }

    /**
     * 显示容器
     */
    private fun showContainer() {
        inputMoreContainer.removeCallbacks(mHideEmotionPanelTask)
        InputMethodUtils.updateSoftInputMethod((context as Activity), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        inputMoreContainer.visibility = View.VISIBLE
        InputMethodUtils.hideKeyboard(inputEditMessage)
    }

    /**
     * 隐藏容器
     */
    private fun hideContainer() {
        if (inputMoreContainer.visibility != View.GONE) {
            inputMoreContainer.visibility = View.GONE
            InputMethodUtils.updateSoftInputMethod((context as Activity), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    /**
     * 修改容器高度
     */
    private fun onUpdateContainerHeight(keyboardHeight: Int) {
        val params = inputMoreContainer.layoutParams
        if (params != null && params.height != keyboardHeight) {
            params.height = keyboardHeight
            inputMoreContainer.layoutParams = params
        }
    }

    private val mHideEmotionPanelTask = Runnable { hideContainer() }


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
        if (mShowHeight > 0 && mLayoutBottom > 0) {
            val h = mHideHeight - mShowHeight
            if (h > 0 && !isHas) {
                mKeyBoardHeight = h
                LogUtil.logd("高度 = $isShow     -----  $mKeyBoardHeight")
                InputMethodUtils.setKeyboardHeight(mKeyBoardHeight, context)
                onUpdateContainerHeight(mKeyBoardHeight)
                isHas = true
            }
        }
        mLayoutBottom = b
    }

}

