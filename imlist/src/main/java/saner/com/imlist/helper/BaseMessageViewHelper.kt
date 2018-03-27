package saner.com.imlist.helper

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import saner.com.imlist.R
import saner.com.imlist.adapter.BaseRecyclerAdapter
import saner.com.imlist.holder.MessageViewHolder
import saner.com.imlist.model.IMessage
import saner.com.imlist.model.MessageDirection
import saner.com.imlist.model.interfaces.ViewHelperListener
import saner.com.imlist.widget.CustomImageView

/**
 * Created by sunset on 2018/3/12.
 */
abstract class BaseMessageViewHelper(adapter: BaseRecyclerAdapter<IMessage>) : MessageViewHelper<BaseRecyclerAdapter<IMessage>, MessageViewHolder, IMessage>(adapter) {

    lateinit var mView: View
    lateinit var mData: IMessage
    lateinit var mContext: Context


    private lateinit var mLeftAvatar: CustomImageView

    private lateinit var mRightAvatar: CustomImageView

    private lateinit var mLayoutContent: FrameLayout

    private lateinit var mProgressBar: ProgressBar

    private lateinit var mSendFailed:ImageView

    override fun convert(holder: MessageViewHolder, data: IMessage, position: Int) {
        mView = holder.getConvertView()
        mContext = mView.context
        mData = data
        inflate()
        refresh()
    }

    private fun inflate() {
        mLeftAvatar = findViewById(R.id.im_base_left_avatar)
        mRightAvatar = findViewById(R.id.im_base_right_avatar)
        mLayoutContent = findViewById(R.id.im_base_content)
        mProgressBar = findViewById(R.id.im_base_load)
        mSendFailed=findViewById(R.id.im_base_send_failed)

        if (mLayoutContent.childCount == 0) {
            View.inflate(mContext, getContentResId(), mLayoutContent)
        }
        inflateContentView()
    }

    private fun refresh() {
        setAvatarView()
        setContentView()
        setOnClick()
        setMessageStatus()
        bindContentView()
    }

    /**
     * 设置消息的状态
     * 这里其实要根据发送的消息状态来设置显示逻辑，我只做了一个模拟消息发送，
     */
    private fun setMessageStatus() {
//        if (isMiddleItem()) {
//            mProgressBar.visibility = View.GONE
//            mSendFailed.visibility=View.GONE
//        } else {
//            if (isMsgDirection()) {
//                mProgressBar.visibility = View.GONE
//                mSendFailed.visibility=View.GONE
//            } else {
//                mProgressBar.visibility = View.VISIBLE
//                Handler().postDelayed({
//                    mProgressBar.visibility = View.GONE
//                }, 2000)
//            }
//        }

    }


    /**
     * 设置列表的点击事件
     */
    private fun setOnClick() {
        val helperListener: ViewHelperListener = getAdapter().getHelperEvent() ?: return
        mLayoutContent.setOnClickListener {
            helperListener.onItemClick(mData)
        }
        mLayoutContent.setOnLongClickListener {
            helperListener.onItemLongClick(mData)
            false
        }
        mLeftAvatar.setOnClickListener {
            helperListener.onLeftAvatar(mData)
        }

        mRightAvatar.setOnClickListener {
            helperListener.onRightAvatar(mData)
        }
    }

    /**
     * 设置内容布局显示
     */
    @SuppressLint("RtlHardcoded")
    private fun setContentView() {
        val bodylayout: LinearLayout = findViewById(R.id.im_base_body)
        if (isMiddleItem()) {
            setGravity(bodylayout, Gravity.CENTER)
        } else {
            if (isMsgDirection()) {
                setGravity(bodylayout, Gravity.LEFT)
                mLayoutContent.setBackgroundResource(leftBackground())
            } else {
                setGravity(bodylayout, Gravity.RIGHT)
                mLayoutContent.setBackgroundResource(rightBackground())
            }
        }
    }


    /**
     * 设置显示自己或者对方的头像
     */
    private fun setAvatarView() {
        val show: CustomImageView = if (isMsgDirection()) mLeftAvatar else mRightAvatar
        val hide: CustomImageView = if (isMsgDirection()) mRightAvatar else mLeftAvatar
        hide.visibility = View.GONE
        if (!isShowHeadImage()) {
            show.visibility = View.GONE
            return
        } else if (isMiddleItem()) {
            show.visibility = View.GONE
        } else {
            show.visibility = View.VISIBLE
            show.setBorderRadius(5f)
            show.setStyleTyoe(CustomImageView.CIRCLE_TYPE)
            getAdapter().getImageLoader().loadImage(show, mData.getUserAvatar())
        }

    }


    /**
     * 得到消息的方向
     */
    protected fun isMsgDirection(): Boolean {
        return mData.getMsgDirection() === MessageDirection.In
    }


    /**
     * 返回该消息是否居中显示
     */
    protected fun isMiddleItem(): Boolean {
        return false
    }

    /**
     * 是否显示头像，默认为显示
     */
    protected fun isShowHeadImage(): Boolean {
        return true
    }


    /**
     * 当是接收到的消息时，内容区域背景的drawable id
     */
    open protected fun leftBackground(): Int {
        return R.drawable.im_message_left_white_bg
    }

    /**
     * 当是发送出去的消息时，内容区域背景的drawable id
     */
    open protected fun rightBackground(): Int {
        return R.drawable.im_message_right_blue_bg
    }

    /**
     * 将消息数据项与内容的view进行绑定
     */
    abstract fun bindContentView()

    /**
     * 子类获取布局控件
     */
    abstract fun inflateContentView()

    /**
     * 获取消息内容的布局资源
     */
    abstract fun getContentResId(): Int

    /**
     * 通过控件Id获取布局View
     */
    protected fun <VIEW : View> findViewById(id: Int): VIEW {
        return mView.findViewById(id)
    }

    /**
     * 设置Message布局显示的方向
     */
    protected fun setGravity(view: View, gravity: Int) {
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = gravity
    }
}

