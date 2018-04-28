package com.saner.imui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.iminput.listener.InputListener
import com.iminput.widget.more.MoreBaseAction
import com.saner.imui.input.action.ImageAction
import com.saner.imui.input.action.VideoAction
import com.saner.imui.list.IMRecyclerAdapter
import com.saner.imui.util.IMConfig
import kotlinx.android.synthetic.main.activity_main.*
import com.imlist.model.IMessage
import com.imlist.model.MessageDirection
import com.imlist.model.MessageType
import com.saner.imui.model.MyMessage
import com.saner.imui.list.helper.PhotoViewHelper
import com.imlist.holder.ViewHelperFactory
import com.imlist.interfaces.ViewHelperListener
import com.imlist.interfaces.Imageloader

class MainActivity : AppCompatActivity(), ViewHelperListener, InputListener, Imageloader {


    private lateinit var mAdapter: IMRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initRecycler()
        loadData()
    }

    private fun loadData() {
        val messages: ArrayList<MyMessage> = ArrayList()
        for (i in 0 until 2) {
            if (i % 2 == 0) {
                messages.add(getMessage(i, MessageDirection.In, MessageType.text, getString(R.string.message)))
            } else {
                messages.add(getMessage(i, MessageDirection.Out, MessageType.image, getString(R.string.message)))
            }
        }
//        messages.add(getMessage(0, MessageDirection.Out, MessageType.image, getString(R.string.message)))
        messages.reverse()
        mAdapter.addMoreMessage(messages)
    }

    private fun initView() {
        input_layout.setInputListener(this)
        input_layout.isNeedVoice(true)
        input_layout.addVoiceLayout(voice_layout)

        val mActions: ArrayList<MoreBaseAction> = ArrayList()
        mActions.add(VideoAction())
        mActions.add(ImageAction())
        mActions.add(VideoAction())
        mActions.add(ImageAction())

        mActions.add(VideoAction())
        mActions.add(ImageAction())
        mActions.add(VideoAction())
        mActions.add(ImageAction())

        mActions.add(VideoAction())
        mActions.add(ImageAction())
        mActions.add(VideoAction())
        mActions.add(ImageAction())

        mActions.add(VideoAction())
        mActions.add(ImageAction())
        mActions.add(VideoAction())
        mActions.add(ImageAction())

        mActions.add(ImageAction())
        input_layout.addMoreActions(mActions)
    }

    private fun initRecycler() {
        val messages: ArrayList<MyMessage> = ArrayList()
        ViewHelperFactory.register(MessageType.image, PhotoViewHelper::class.java)
        mAdapter = IMRecyclerAdapter(messages)
        recycler_view.setAdapter(mAdapter)
        mAdapter.setHelperEvent(this)
        mAdapter.setImageLoader(this)
    }


    private fun getMessage(i: Int, direction: MessageDirection, messageType: MessageType, content: String): MyMessage {
        val message = MyMessage()
        message.setMsgId(i.toString())
        message.setMsgType(messageType)
        message.setMsgText(content)
        message.setMsgDirection(direction)
        if (MessageDirection.Out == direction) {
            message.setUserAvatar("http://img3.imgtn.bdimg.com/it/u=1486014782,3060017975&fm=27&gp=0.jpg")
        } else {
            message.setUserAvatar("http://img.besoo.com/file/201705/07/0807581245908.jpg")
        }
        return message
    }


    override fun onRightAvatar(message: IMessage) {
        toast("点击了自己的头像")
    }

    override fun onLeftAvatar(message: IMessage) {
        toast("点击了好友的头像")
    }

    override fun onItemClick(message: IMessage) {
        when (message.getMsgType()) {
            MessageType.text -> toast("点击了第${message.getMsgId()}条消息，是一个文本消息")
            MessageType.image -> toast("点击了第${message.getMsgId()}条消息，是一个图片消息")
            MessageType.audio -> toast("点击了第${message.getMsgId()}条消息，是一个语音消息")
        }

    }

    override fun onItemLongClick(message: IMessage) {
        toast("长按了第${message.getMsgId()}条消息")
    }


    override fun onLoadMore() {
        loadMoreBar.visibility = View.VISIBLE
        Handler().postDelayed({
            val messages = ArrayList<MyMessage>()
            for (i in 1..3) {
                messages.add(getMessage(i, MessageDirection.Out, MessageType.text, "加载更多"))
            }
            mAdapter.addMoreMessage(messages)
            loadMoreBar.visibility = View.GONE
        }, 1000)

    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        if (recyclerView!!.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
            if (dy <= 0) {
                hideInput()
            }
        }
    }

    override fun loadImage(imageView: ImageView, url: String) {
        IMConfig.loadImage(imageView, url)
    }


    private fun toast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    /**
     * 隐藏底部布局
     */
    private fun hideInput() {
        input_layout.hideInputLayout()
    }

    override fun onSoftKeyboardStatus(status: Boolean) {
        if (status) {
            recycler_view.toPosition(0)
        }
    }

    override fun onTextSend(text: String) {
        mAdapter.addNewMessage(getMessage(9527, MessageDirection.Out, MessageType.text, text))

    }

    override fun onVoiceSend() {
        mAdapter.addNewMessage(getMessage(9527, MessageDirection.Out, MessageType.text, "语音消息"))
    }

    override fun <T> onMoreSend(data: T) {
        val message: MyMessage = data as MyMessage
        mAdapter.addNewMessage(message)
    }

}
