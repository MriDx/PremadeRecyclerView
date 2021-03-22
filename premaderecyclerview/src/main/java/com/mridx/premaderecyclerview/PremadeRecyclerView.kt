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

    fun render() = run { this.adapter = premadeRecyclerAdapter }

    fun setItemCount(count: Int) = run {
        premadeRecyclerAdapter.items += count
        post { premadeRecyclerAdapter.notifyDataSetChanged() }
    }


}

class PremadeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var builder: ((parent: ViewGroup, viewType: Int) -> View?)? = null
    fun itemBuilder(builder: ((parent: ViewGroup, viewType: Int) -> View?)) {
        this.builder = builder
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
        if (position == items - 1) {
            lastItemScrolled?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return items
    }

    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        return position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}