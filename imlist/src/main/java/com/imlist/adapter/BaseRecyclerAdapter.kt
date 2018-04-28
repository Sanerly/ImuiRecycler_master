package com.imlist.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import saner.com.imlist.R
import com.imlist.helper.BaseMessageViewHelper
import com.imlist.holder.MessageViewHolder
import com.imlist.holder.ViewHelperFactory
import com.imlist.interfaces.Imageloader
import com.imlist.interfaces.ScrollMoreListener.OnLoadMoreListener
import com.imlist.interfaces.ViewHelperListener
import com.imlist.model.IMessage
import java.lang.Exception
import java.util.*


abstract class BaseRecyclerAdapter<in T: IMessage>(messages: ArrayList<T>) : RecyclerView.Adapter<MessageViewHolder>(), OnLoadMoreListener {

    /**
     * viewType->布局
     */
    private var layouts: SparseArray<Int>? = null

    /**
     * viewType->helper类
     */
    private var helperClasses: SparseArray<Class<out BaseMessageViewHelper>>? = null

    /**
     * viewType->实例化helper
     */
    private var typeViewHelper: MutableMap<Int, HashMap<String, BaseMessageViewHelper>>? = null


    /**
     * 接口设置可为空
     */
    private var helperListener: ViewHelperListener? = null
    /**
     * 提供外部加载图片使用，可以使用任何加载图片框架
     */
    private lateinit var mImageloader: Imageloader

    private lateinit var mContext: Context

    private lateinit var mLayoutInflater: LayoutInflater

    private lateinit var mLayoutManager: LinearLayoutManager

    private var mMessages: ArrayList<T> = messages


    private var helperViewType: MutableMap<Class<out BaseMessageViewHelper>, Int>

    init {

        //反转集合中的数据
        mMessages.reverse()
        helperViewType = HashMap()
        val list: List<Class<out BaseMessageViewHelper>> = ViewHelperFactory.getAllViewHolders()
        var viewType = 0
        for (helper: Class<out BaseMessageViewHelper> in list) {
            viewType++
            addItemType(viewType, R.layout.im_base_layout, helper)
            helperViewType[helper] = viewType
        }

    }

    override fun getItemViewType(position: Int): Int {
        return helperViewType[ViewHelperFactory.getViewHolderByType(mMessages[position])]!!
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MessageViewHolder {
        this.mLayoutInflater = LayoutInflater.from(mContext)
        return onCreateBaseViewHolder(parent!!, viewType)
    }

    override fun onBindViewHolder(holder: MessageViewHolder?, position: Int) {
        val itemType: Int = holder!!.itemViewType
        val itemKey: String = getItemKey(mMessages[position])
        var helper: BaseMessageViewHelper? = typeViewHelper?.get(itemType)?.get(itemKey)
        if (helper == null) {
            try {
                val cls: Class<out BaseMessageViewHelper> = helperClasses!!.get(itemType)
                val constructor = cls.declaredConstructors[0]
                constructor.isAccessible = true
                helper = constructor.newInstance(this) as BaseMessageViewHelper?
                typeViewHelper!![itemType]!![itemKey] = helper!!
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (helper != null) {
            helper.convert(holder, mMessages[position], position)
        }
    }


    /**
     * 这里获取layouts中存储的布局资源，生成View，放入MessageViewHolder中
     */
    private fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        return MessageViewHolder(getItemView(layouts!![viewType], parent))
    }


    private fun getItemView(layoutResId: Int, parent: ViewGroup): View {
        return mLayoutInflater.inflate(layoutResId, parent, false)
    }


    private fun addItemType(type: Int, layout: Int, helper: Class<out BaseMessageViewHelper>) {
        if (layouts == null) {
            layouts = SparseArray()
        }
        layouts!!.put(type, layout)

        if (helperClasses == null) {
            helperClasses = SparseArray()
        }
        helperClasses!!.put(type, helper)


        if (typeViewHelper == null) typeViewHelper = HashMap()
        typeViewHelper!![type] = HashMap()
    }


    /**
     * 设置Context
     */
    fun setContext(context: Context) {
        this.mContext = context
    }

    /**
     * 设置列表的管理器
     */
    fun setLayoutManager(manager: LinearLayoutManager) {
        this.mLayoutManager = manager
    }

    /**
     * 添加一条Message，刷新，滚动到该消息
     */
    fun addNewMessage(data: T) {
        mMessages.add(0, data)
        notifyItemRangeInserted(0, 1)
        mLayoutManager.scrollToPosition(0)
    }

    /**
     * 添加多条Message,显示加载的第一条消息
     */
    fun addMoreMessage(data: List<T>) {
        val oldSize: Int = mMessages.size
        mMessages.addAll(data)
        notifyItemRangeInserted(oldSize, mMessages.size - oldSize)
        mLayoutManager.scrollToPosition(oldSize)
    }


    override fun onLoadMore() {
        helperListener!!.onLoadMore()
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        helperListener!!.onScrolled(recyclerView, dx, dy)
    }

    /**
     * 设置Helper里的点击通知
     */
    fun setHelperEvent(helperListener: ViewHelperListener) {
        this.helperListener = helperListener
    }

    /**
     * 获取事件
     */
    fun getHelperEvent(): ViewHelperListener? {
        if (helperListener != null) {
            return helperListener as ViewHelperListener
        }
        return null
    }


    /**
     * 设置加载图片设置
     */
    fun setImageLoader(imageloader: Imageloader) {
        this.mImageloader = imageloader
    }

    /**
     * 获取加载图片Imageloader的实例
     */
    fun getImageLoader(): Imageloader {
        return this.mImageloader
    }

    /**
     * 返回一个键
     */
    protected abstract fun getItemKey(data: T): String

}


