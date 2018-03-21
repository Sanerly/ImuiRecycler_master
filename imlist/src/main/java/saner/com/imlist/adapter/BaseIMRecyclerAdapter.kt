package saner.com.imlist.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import saner.com.imlist.R
import saner.com.imlist.helper.BaseMessageViewHelper
import saner.com.imlist.holder.MessageViewHolder
import saner.com.imlist.holder.ViewHelperFactory
import saner.com.imlist.model.IMessage
import saner.com.imlist.model.interfaces.IMListEventListener
import saner.com.imlist.model.interfaces.Imageloader
import saner.com.imlist.model.interfaces.ScrollMoreListener.OnLoadMoreListener
import java.lang.Exception
import java.util.*


abstract class BaseIMRecyclerAdapter<in T : IMessage>(datas: ArrayList<T>) : RecyclerView.Adapter<MessageViewHolder>(), OnLoadMoreListener {

    /**
     * viewType->layoutResId
     */
    private var layouts: SparseArray<Int>? = null

    /**
     * viewType->view holder class
     */
    private var helperClasses: SparseArray<Class<out BaseMessageViewHelper>>? = null

    /**
     * viewType->view holder instance
     */
    private var typeViewHelper: MutableMap<Int, HashMap<String, BaseMessageViewHelper>>? = null

    /**
     * 接口设置可为空
     */
    private var helperListener: IMListEventListener? = null
    /**
     * 提供外部加载图片使用，可以使用任何加载图片框架
     */
    private lateinit var mImageloader: Imageloader

    private lateinit var mContext: Context

    private lateinit var mLayoutInflater: LayoutInflater

    private lateinit var mLayoutManager: LinearLayoutManager

    private var mDatas:ArrayList<T> = datas


    private val helperViewType: MutableMap<Class<out BaseMessageViewHelper>, Int>

    init {
        //反转集合中的数据
        mDatas.reverse()

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
        return helperViewType[ViewHelperFactory.getViewHolderByType(mDatas[position])]!!
    }

    override fun getItemCount(): Int {

        return mDatas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MessageViewHolder {
        this.mLayoutInflater = LayoutInflater.from(mContext)
        return onCreateBaseViewHolder(parent!!, viewType)
    }

    override fun onBindViewHolder(holder: MessageViewHolder?, position: Int) {
        val itemType: Int = holder!!.itemViewType
        val itemKey: String = getItemKey(mDatas[position])
        var helper: BaseMessageViewHelper? = typeViewHelper?.get(itemType)?.get(itemKey)
        if (helper == null) {
            try {
                val cls: Class<out BaseMessageViewHelper> = helperClasses!!.get(itemType)
                val constructor = cls.declaredConstructors[0]
                constructor.isAccessible = true
                helper = constructor.newInstance(this) as BaseMessageViewHelper?
                typeViewHelper!![itemType]!!.put(itemKey, helper!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (helper != null) {
            helper.convert(holder, mDatas[position], position)
        }

    }


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
        typeViewHelper!!.put(type, HashMap())
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
        mDatas.add(0,data)
        notifyItemRangeInserted(0, 1)
        mLayoutManager.scrollToPosition(0)
    }

    /**
     * 添加多条Message
     */
    fun addMoreMessage(data:List<T>) {
       val oldSize: Int=mDatas.size
        mDatas.addAll(data)
        notifyItemRangeInserted(oldSize, mDatas.size - oldSize)
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
    fun setHelperEvent(helperListener: IMListEventListener) {
        this.helperListener = helperListener
    }

    /**
     * 获取事件
     */
    fun getHelperEvent(): IMListEventListener? {
        if (helperListener != null) {
            return helperListener as IMListEventListener
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

    protected abstract fun getItemKey(data: T): String
}