package com.mridx.premaderecyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class PremadeRecyclerView : RecyclerView {


    var itemBuilder: ((parent: ViewGroup, index: Int) -> View?)? = null
        set(value) {
            field = value
            premadeRecyclerAdapter.itemBuilder(field!!)
        }

    var itemBuilderWithItem: ((parent: ViewGroup, item: Any) -> View?)? = null
        set(value) {
            field = value
            premadeRecyclerAdapter.itemBuilderWithItem(field!!)
        }

    fun onLastItemScrolled(lastItemScrolled: ((position: Int) -> Unit)) {
        premadeRecyclerAdapter.lastItemScrolled(lastItemScrolled)
    }

    private val premadeRecyclerAdapter by lazy {
        PremadeRecyclerAdapter()
    }

    constructor(context: Context) : super(context) {
        render(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        render(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        render(context, attrs, defStyleAttr)
    }

    private fun render(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        this.layoutManager = LinearLayoutManager(context)
    }

    /**
     * internally sets adapter to recyclerview
     */
    fun render() = run { this.adapter = premadeRecyclerAdapter }

    /**
     * addMoreItems
     * adds more items to PremadeRecyclerView
     * @param       count       the count of items to be added
     */
    fun addMoreItems(count: Int) {
        premadeRecyclerAdapter.items += count
        post { premadeRecyclerAdapter.notifyDataSetChanged() }
    }

    /**
     * setItemCount
     * sets the count of items to PremadeRecyclerView to render initially
     * @param       count       the count of items to be added
     *
     * Note:- should be called only once
     */
    fun setItemCount(count: Int) = run {
        premadeRecyclerAdapter.items = count
        post { premadeRecyclerAdapter.notifyDataSetChanged() }
    }

    /**
     * setItems
     * sets list of items to render in PremadeRecyclerView
     * @param       itemList        the list of items, item can be anything
     *
     * * Note:- should be called only once
     */
    fun setItems(itemList: List<Any>) {
        premadeRecyclerAdapter.itemList = itemList as ArrayList<Any>
        post { premadeRecyclerAdapter.notifyDataSetChanged() }
    }

    /**
     * addMoreItems
     * adds more items to PremadeRecyclerView
     * @param       itemList        the list of items, item can be anything
     */
    fun addMoreItems(itemList: List<Any>) {
        premadeRecyclerAdapter.itemList.addAll(itemList)
        post { premadeRecyclerAdapter.notifyDataSetChanged() }
    }

    /**
     * addMoreItems
     * adds more items to PremadeRecyclerView
     * @param       itemList        the list of items, item can be anything
     * @param       notifyItemRangeChanged      boolean value, true to animate item range change
     */
    fun addMoreItems(itemList: List<Any>, notifyItemRangeChanged: Boolean) {
        val prevLastPosition = premadeRecyclerAdapter.itemList.size - 1
        premadeRecyclerAdapter.itemList.addAll(itemList)
        post {
            if (notifyItemRangeChanged)
                premadeRecyclerAdapter.notifyItemRangeChanged(
                    prevLastPosition + 1,
                    premadeRecyclerAdapter.itemList.size - 1
                )
            else
                premadeRecyclerAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Simple adapter specific function to notify the adapter that its associated data set has changed
     */
    fun itemSetChanged() = post { premadeRecyclerAdapter.notifyDataSetChanged() }

    /**
     * Simple adapter specific function to notify the adapter that an item
     *      of its associated data set has changed
     *
     * @param       position        position of changed item
     */
    fun itemChanged(position: Int) {
        post { premadeRecyclerAdapter.notifyItemChanged(position) }
    }

    /**
     * Simple adapter specific function to notify the adapter that a range of items
     *      of its associated data set has changed
     *
     * @param       startPosition        start position of changed items
     * @param       count       count of changed items
     */
    fun itemChanged(startPosition: Int, count: Int) {
        post { premadeRecyclerAdapter.notifyItemRangeChanged(startPosition, count) }
    }

    /**
     * Simple adapter specific function to notify the adapter that a new item has inserted
     *      to its associated data set
     *
     * @param       position        position of the newly inserted item
     */
    fun itemAdded(position: Int) {
        post { premadeRecyclerAdapter.notifyItemInserted(position) }
    }

    /**
     * Simple adapter specific function to notify the adapter that a range of items has inserted
     *      to its associated data set
     *
     * @param       startPosition        start position of newly inserted items
     * @param       count       count of the newly inserted items
     */
    fun itemsAdded(startPosition: Int, count: Int) {
        post { premadeRecyclerAdapter.notifyItemRangeInserted(startPosition, count) }
    }

    /**
     * Simple adapter specific function to notify the adapter that a item has removed
     *      from its associated data set
     *
     * @param       position        position of the removed item
     */
    fun itemRemoved(position: Int) {
        post { premadeRecyclerAdapter.notifyItemRemoved(position) }
    }

    /**
     * Simple adapter specific function to notify the adapter that a range of items has removed
     *      from its associated data set
     *
     * @param       startPosition        start position of the removed items
     * @param       count       count of removed items
     */
    fun itemRemoved(startPosition: Int, count: Int) {
        post { premadeRecyclerAdapter.notifyItemRangeRemoved(startPosition, count) }
    }

    /**
     * to know more about adapter specific functions used above here
     * kindly refer to this -> https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter
     */


}

class PremadeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var builder: ((parent: ViewGroup, viewType: Int) -> View?)? = null
    fun itemBuilder(builder: ((parent: ViewGroup, viewType: Int) -> View?)) {
        this.builder = builder
    }

    private var builderWithItem: ((parent: ViewGroup, item: Any) -> View?)? = null
    fun itemBuilderWithItem(builder: ((parent: ViewGroup, item: Any) -> View?)) {
        this.builderWithItem = builder
    }

    private var lastItemScrolled: ((position: Int) -> Unit)? = null
    fun lastItemScrolled(listener: ((position: Int) -> Unit)) {
        this.lastItemScrolled = listener
    }

    var items: Int = 0
        set(value) {
            field = value
        }
        get() = field

    var itemList: ArrayList<Any> = ArrayList()
        set(value) {
            field.addAll(value)
        }
        get() = field


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (itemList.isNotEmpty()) {
            builderWithItem?.let {
                it.invoke(parent, itemList[viewType])?.let { v ->
                    return ViewHolder(v)
                }
            }
        }
        builder?.invoke(parent, viewType)?.let {
            return ViewHolder(it)
        }
        return ViewHolder(MaterialTextView(parent.context).apply {
            this.text = "Please return a view from itemBuilder"
        }.also {
            it.layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
            )
        })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (itemList.isEmpty()) {
            if (position == items - 1) {
                lastItemScrolled?.invoke(position)
            }
        } else {
            if (position == itemList.size - 1) {
                lastItemScrolled?.invoke(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (itemList.isEmpty()) items else itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}