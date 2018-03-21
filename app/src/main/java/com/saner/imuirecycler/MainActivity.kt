package com.saner.imuirecycler

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import com.saner.imuirecycler.list.IMRecyclerAdapter
import com.saner.imuirecycler.util.IMConfig
import kotlinx.android.synthetic.main.activity_main.*
import saner.com.imlist.model.IMessage
import saner.com.imlist.model.MessageDirection
import saner.com.imlist.model.MessageType
import com.saner.imuirecycler.model.MyMessage
import junit.framework.Assert
import saner.com.imlist.helper.PhotoViewHelper
import saner.com.imlist.holder.ViewHelperFactory
import saner.com.imlist.model.interfaces.IMListEventListener
import saner.com.imlist.model.interfaces.Imageloader
import saner.com.imlist.model.interfaces.KeyBoardObserver
import saner.com.imlist.model.interfaces.KeyBoardObserver.KeyBoardListener

class MainActivity : AppCompatActivity(), IMListEventListener, KeyBoardListener, Imageloader {


    private lateinit var mDatas: ArrayList<IMessage>
    private lateinit var mAdapter: IMRecyclerAdapter
    private lateinit var mKeyboard: KeyBoardObserver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initSendMessage()
        initKeyBoard()
    }

    private fun initKeyBoard() {
        mKeyboard = KeyBoardObserver(this)
        mKeyboard.setKeyBoardListener(this)
    }

    override fun onKeyboardChange(isShow: Boolean, keyboardHeight: Int) {
        if (isShow) {
            recycler_view.toPosition(0)
        }
//        toast("软键盘的高度--------" + keyboardHeight)
    }

    private fun initData() {
        mDatas = ArrayList()
        for (i in 1..30) {
            if (i % 2 == 0) {
                mDatas.add(getMessage(i, MessageDirection.Out, MessageType.image, getString(R.string.message)))
            } else {
                mDatas.add(getMessage(i, MessageDirection.In, MessageType.text, getString(R.string.message)))
            }
        }

        ViewHelperFactory.register(MessageType.image, PhotoViewHelper::class.java)

        mAdapter = IMRecyclerAdapter(mDatas)
        recycler_view.setAdapter(mAdapter)
        mAdapter.setHelperEvent(this)
        mAdapter.setImageLoader(this)
    }

    private fun initSendMessage() {

        but_send.setOnClickListener {
            val content = edit_import.text.toString()
            mAdapter.addNewMessage(getMessage(mDatas.size, MessageDirection.Out, MessageType.text, content))
            edit_import.setText("")
        }
    }

    private fun getMessage(i: Int, direction: MessageDirection, messageType: MessageType, content: String): IMessage {
        val message = MyMessage()
        message.setMsgId(i.toString())
        message.setMsgType(messageType)
        message.setMsgText("$i   $content")
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
        if (message.getMsgType() == MessageType.text) {
            toast("点击了第${message.getMsgId()}条消息，是一个文本消息")
        } else if (message.getMsgType() == MessageType.image) {
            toast("点击了第${message.getMsgId()}条消息，是一个图片消息")
        } else if (message.getMsgType() == MessageType.audio) {
            toast("点击了第${message.getMsgId()}条消息，是一个语音消息")
        }

    }

    override fun onItemLongClick(message: IMessage) {
        toast("长按了第${message.getMsgId()}条消息")
    }


    override fun onLoadMore() {
        loadMoreBar.visibility=View.VISIBLE
        Handler().postDelayed({
            val datas = ArrayList<IMessage>()
            for (i in 1..10) {
                datas.add(getMessage(i, MessageDirection.Out, MessageType.text, "加载更多"))
            }
            mAdapter.addMoreMessage(datas)
            loadMoreBar.visibility=View.GONE
        }, 1000)

    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        if (recyclerView!!.scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
            if (dy > 0) {
            } else {
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

    private fun hideInput() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(but_send.windowToken, 0)
        but_send.clearFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        mKeyboard.destroy()
    }
}
